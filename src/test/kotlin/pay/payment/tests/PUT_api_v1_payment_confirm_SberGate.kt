package pay.payment.tests

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide.*
import com.codeborne.selenide.SelenideElement
import io.restassured.module.jsv.JsonSchemaValidator
import io.restassured.path.json.JsonPath
import io.restassured.response.Response
import org.hamcrest.Matchers.equalTo

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Tag

import pay.xprojectdata.client.ApiClient
import pay.xprojectdata.config.EnvironmentConfig
import pay.xprojectdata.dto.request.SberGateRequestGenerator
import pay.xprojectdata.dto.request.sberGateBodyRequestForConfirmPutRequestGenerator
import ru.testit.annotations.WorkItemIds

import kotlin.test.assertNotNull

class PUTApiV1PaymentConfirmSberGate {
    private lateinit var apiClient: ApiClient
    private var extractedId: String = ""

    // Валидация Json
    private fun getJsonPath(response: Response): JsonPath {
        val json = response.asString()
        return JsonPath.from(json)
    }

    // Логгер
    companion object {
        @BeforeAll
        @JvmStatic
        fun globalSetup() {
            configLogger.globalSetup()
        }
    }

    @BeforeEach
    fun setUp() {
        val config = EnvironmentConfig.getConfigFromEnvVar()
        apiClient = ApiClient(config)
    }

    @AfterEach
    fun tearDown() {
        try {
            // Закрываем HTTP‑соединения
            io.restassured.RestAssured.reset()

            // Принудительное завершение потоков (если есть кастомные пулы)
            // ExecutorService?.shutdown()
        } catch (e: Exception) {
            println("Ошибка при очистке: ${e.message}")
        }
    }

    // Проверка статуса транзакции с умным ожиданием
    private fun checkTransactionStatus(
        extractedId: String,
        expectedStatus: Int,
        expectedStatusText: String,
        checkName: String, // Для логирования
        // Умное ожидание
        maxTimeoutSeconds: Long = 120,
        initialDelayMillis: Long = 1000,
        multiplier: Double = 1.5

    ) {
        println("Выполняем проверку: $checkName")

        var currentDelay = initialDelayMillis
        val startTime = System.currentTimeMillis()

        while (System.currentTimeMillis() - startTime < maxTimeoutSeconds * 1000) {
            try {
                // Выполняем запрос статуса
                val basePathForCheckTransactionStatus = "/api/v1/payment/status/$extractedId"
                val responseGetCheckTransaction: Response = apiClient.get(
                    path = basePathForCheckTransactionStatus,
                    headers = emptyMap<String, String>() // Явное указание типов
                )

                // Валидация ответа
                responseGetCheckTransaction
                    .then()
                    .statusCode(200)
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(
                        "pay.payment.jsonschema/GET_api_v1_payment_status_id_SberGate.json"
                    ))

                val jsonBody = JsonPath.from(responseGetCheckTransaction.body.asString())

                // Извлечение данных
                val jsonPathStatus = getJsonPath(responseGetCheckTransaction)
                val status = jsonPathStatus.getInt("status")
                val statusText = jsonPathStatus.getString("statusText")

                // Проверка обязательных полей (выполняем при каждом запросе)
                assertNotNull(jsonBody.get("id"), "Поле 'id' не должно быть null")
                assertTrue(jsonBody.getString("transaction_id").isNotEmpty(), "Поле 'transaction_id' не должно быть пустым")
                assertTrue(jsonBody.getString("statusText").isNotEmpty(), "Поле 'statusText' не должно быть пустым")
                assertTrue(jsonBody.getString("updated").isNotEmpty(), "Поле 'updated' не должно быть пустым")
                assertTrue(jsonBody.getString("payment_details.system_id").isNotEmpty(), "Поле 'payment_details.system_id' не должно быть пустым")

                // Проверяем, достигнут ли ожидаемый статус
                if (status == expectedStatus && statusText == expectedStatusText) {
                    println("Статус $expectedStatus достигнут за ${(System.currentTimeMillis() - startTime) / 1000} сек.")
                    return // Успешно — выходим из функции
                }

                println("Текущий статус: $status (ожидаем $expectedStatus), текст: '$statusText'. Следующая проверка через $currentDelay мс...")

            } catch (e: Exception) {
                println("Ошибка при проверке статуса: ${e.message}. Повторная попытка через $currentDelay мс...")
            }

            // Ждём с текущей задержкой
            try {
                Thread.sleep(currentDelay)
            } catch (e: InterruptedException) {
                e.printStackTrace()
                throw AssertionError("Ожидание статуса прервано: ${e.message}")
            }

            // Увеличиваем задержку (с ограничением в 10 секунд)
            currentDelay = (currentDelay * multiplier).toLong().coerceAtMost(10_000)
        }

        // Если вышли из цикла — таймаут
        throw AssertionError(
            "Таймаут: статус $expectedStatus не достигнут за $maxTimeoutSeconds секунд. " +
                    "Последняя проверка показала статус, отличный от ожидаемого."
        )
    }

    @Test
    @DisplayName("202 - Успешное проведение платежа Картой СГ")
    fun successfulSberGateCardPayment() {
        val basePath = "/api/v1/payment/pay/d96d0e7f-771a-4c85-9f13-5eda4bca9251"
        //Подготовка тела запроса
        val requestBody = SberGateRequestGenerator.baseRequest()
        //Выполнение POST-запроса
        val response: Response = apiClient.post(
            path = basePath,
            body = requestBody
        )
        //Валидация ответа и извлечение url
        response.then()
            .statusCode(202)
            .body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath(
                    "pay.payment.jsonschema/POST_api_v1_payment_pay_method_uuid_SberGate.json"
                )
            )
            .extract() //Извлекаем ответ после валидации
            .jsonPath() //Получаем JsonPath

        val jsonPathPost = getJsonPath(response)
        val extractedUrl = jsonPathPost.getString("url")
        val extractedId = jsonPathPost.getString("id")

        println("Извлечённый URL: $extractedUrl")
        println("Извлечённый ID: $extractedId")

        assertNotNull(extractedUrl, "Поле url = Null")
        assertTrue(extractedUrl.isNotEmpty(), "Поле url пустое")

        assertNotNull(extractedId, "Поле id = Null")
        assertTrue(extractedId.isNotEmpty(), "Поле id пустое")

        //Открываем страницу по извлечённому URL
        open(extractedUrl)

        // Находим элементы на странице и вводим данные
        val elementCardNumber: SelenideElement = `$`("input[autocomplete='cc-number']").setValue("2202205000012424")// Карта без ACS кода
        elementCardNumber.should(Condition.exist)
        elementCardNumber.click()

        val elementCardName: SelenideElement = `$`("input[autocomplete='cc-name']").setValue("zxc zxc")// Имя
        elementCardName.should(Condition.exist)
        elementCardName.click()

        val elementCardExpiration: SelenideElement = `$`("input[autocomplete='cc-exp']").setValue("0535")// Срок
        elementCardExpiration.should(Condition.exist)
        elementCardExpiration.click()

        val elementCardCVV: SelenideElement = `$`(".inputs_back_cvv input").setValue("669")// CVV
        elementCardCVV.should(Condition.exist)
        elementCardCVV.click()

        val elementButtonSubmit: SelenideElement = `$`(".footer_buttons button")
        elementButtonSubmit.should(Condition.exist)
        elementButtonSubmit.click()

        //Проверяем статус транзакции = 3
        checkTransactionStatus(extractedId, 3, "Холдирование", "Проверка статуса (холдирование)")

        //Подготовка тела PUT запроса на подтверждение платежа
        val basePathForPut = "/api/v1/payment/confirm"
        val modifiedRequestBodyPut = sberGateBodyRequestForConfirmPutRequestGenerator.baseRequest().copy(
            amount = 2,
            id = extractedId.toIntOrNull()
        )
        //Выполнение PUT-запроса
        val responsePut: Response = apiClient.put(
            path = basePathForPut,
            body = modifiedRequestBodyPut
        )
        // Валидация ответа
        responsePut.then()
            .statusCode(200)
            .body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath(
                    "pay.payment.jsonschema/PUT_api_v1_payment_confirm.json"
                )
            )
        // Извлечение данных
        val jsonPathPut = getJsonPath(responsePut)
        val success = jsonPathPut.getBoolean("success")

        assertNotNull(success, "Поле success = Null")
        assertTrue(success, "Ожидалось true, но получено: $success")

        // Проверка статуса транзакции = 5
        checkTransactionStatus(extractedId, 5, "Списание успешно", "Проверка статуса Списание успешно")
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("58c8588e-c8e5-40de-91d0-89e76d7657f5")
    @DisplayName("200. Успешное выполнение запроса Картой СГ.")
    fun successConfirmAmountEqualOrderSberGate() {
        val basePath = "/api/v1/payment/pay/d96d0e7f-771a-4c85-9f13-5eda4bca9251"
        val requestBody = SberGateRequestGenerator.baseRequest()
        val response: Response = apiClient.post(
            path = basePath,
            body = requestBody
        )
        response.then()
            .statusCode(202)
            .body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath(
                    "pay.payment.jsonschema/POST_api_v1_payment_pay_method_uuid_SberGate.json"
                )
            )
            .extract()
            .jsonPath()

        val jsonPathPost = getJsonPath(response)
        val extractedUrl = jsonPathPost.getString("url")
        val extractedId = jsonPathPost.getString("id")

        println("Извлечённый URL: $extractedUrl")
        println("Извлечённый ID: $extractedId")

        assertNotNull(extractedUrl, "Поле url = Null")
        assertTrue(extractedUrl.isNotEmpty(), "Поле url пустое")

        assertNotNull(extractedId, "Поле id = Null")
        assertTrue(extractedId.isNotEmpty(), "Поле id пустое")

        open(extractedUrl)

        // Находим элементы на странице и вводим данные
        val elementCardNumber: SelenideElement = `$`("input[autocomplete='cc-number']").setValue("2202205000012424")// Карта без ACS кода
        elementCardNumber.should(Condition.exist)
        elementCardNumber.click()

        val elementCardName: SelenideElement = `$`("input[autocomplete='cc-name']").setValue("zxc zxc")// Имя
        elementCardName.should(Condition.exist)
        elementCardName.click()

        val elementCardExpiration: SelenideElement = `$`("input[autocomplete='cc-exp']").setValue("0535")// Срок
        elementCardExpiration.should(Condition.exist)
        elementCardExpiration.click()

        val elementCardCVV: SelenideElement = `$`(".inputs_back_cvv input").setValue("669")// CVV
        elementCardCVV.should(Condition.exist)
        elementCardCVV.click()

        val elementButtonSubmit: SelenideElement = `$`(".footer_buttons button")
        elementButtonSubmit.should(Condition.exist)
        elementButtonSubmit.click()

        checkTransactionStatus(extractedId, 3, "Холдирование", "Проверка статуса (холдирование)")

        val basePathForPut = "/api/v1/payment/confirm"
        val modifiedRequestBodyPut = sberGateBodyRequestForConfirmPutRequestGenerator.baseRequest().copy(
            amount = 2,
            id = extractedId.toIntOrNull()
        )
        val responsePut: Response = apiClient.put(
            path = basePathForPut,
            body = modifiedRequestBodyPut
        )
        responsePut.then()
            .statusCode(200)
            .body("success", equalTo(true))

    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("44ff0e59-b346-41af-9a4c-e054fe573d4d")
    @DisplayName("200. Подтверждение платежа с передачей amount<суммы заказа в запросе Картой СГ")
    fun successConfirmAmountLessThanOrderSberGate() {
        val basePath = "/api/v1/payment/pay/d96d0e7f-771a-4c85-9f13-5eda4bca9251"
        val requestBody = SberGateRequestGenerator.baseRequest()
        val response: Response = apiClient.post(
            path = basePath,
            body = requestBody
        )
        response.then()
            .statusCode(202)
            .body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath(
                    "pay.payment.jsonschema/POST_api_v1_payment_pay_method_uuid_SberGate.json"
                )
            )
            .extract()
            .jsonPath()

        val jsonPathPost = getJsonPath(response)
        val extractedUrl = jsonPathPost.getString("url")
        val extractedId = jsonPathPost.getString("id")

        println("Извлечённый URL: $extractedUrl")
        println("Извлечённый ID: $extractedId")

        assertNotNull(extractedUrl, "Поле url = Null")
        assertTrue(extractedUrl.isNotEmpty(), "Поле url пустое")

        assertNotNull(extractedId, "Поле id = Null")
        assertTrue(extractedId.isNotEmpty(), "Поле id пустое")

        open(extractedUrl)

        // Находим элементы на странице и вводим данные
        val elementCardNumber: SelenideElement = `$`("input[autocomplete='cc-number']").setValue("2202205000012424")// Карта без ACS кода
        elementCardNumber.should(Condition.exist)
        elementCardNumber.click()

        val elementCardName: SelenideElement = `$`("input[autocomplete='cc-name']").setValue("zxc zxc")// Имя
        elementCardName.should(Condition.exist)
        elementCardName.click()

        val elementCardExpiration: SelenideElement = `$`("input[autocomplete='cc-exp']").setValue("0535")// Срок
        elementCardExpiration.should(Condition.exist)
        elementCardExpiration.click()

        val elementCardCVV: SelenideElement = `$`(".inputs_back_cvv input").setValue("669")// CVV
        elementCardCVV.should(Condition.exist)
        elementCardCVV.click()

        val elementButtonSubmit: SelenideElement = `$`(".footer_buttons button")
        elementButtonSubmit.should(Condition.exist)
        elementButtonSubmit.click()

        checkTransactionStatus(extractedId, 3, "Холдирование", "Проверка статуса (холдирование)")

        val basePathForPut = "/api/v1/payment/confirm"
        val modifiedRequestBodyPut = sberGateBodyRequestForConfirmPutRequestGenerator.baseRequest().copy(
            amount = 1,
            id = extractedId.toIntOrNull()
        )
        val responsePut: Response = apiClient.put(
            path = basePathForPut,
            body = modifiedRequestBodyPut
        )
        responsePut.then()
            .statusCode(200)
            .body("success", equalTo(true))

    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("a62a5e89-03d1-4e4a-9074-efb15ba82fe7")
    @DisplayName("200. Подтверждение транзакции, находящейся в статусе Списание успешно Картой СГ")
    fun successDoubleConfirmSberGate() {
        val basePath = "/api/v1/payment/pay/d96d0e7f-771a-4c85-9f13-5eda4bca9251"
        val requestBody = SberGateRequestGenerator.baseRequest()
        val response: Response = apiClient.post(
            path = basePath,
            body = requestBody
        )
        response.then()
            .statusCode(202)
            .body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath(
                    "pay.payment.jsonschema/POST_api_v1_payment_pay_method_uuid_SberGate.json"
                )
            )
            .extract()
            .jsonPath()

        val jsonPathPost = getJsonPath(response)
        val extractedUrl = jsonPathPost.getString("url")
        val extractedId = jsonPathPost.getString("id")

        println("Извлечённый URL: $extractedUrl")
        println("Извлечённый ID: $extractedId")

        assertNotNull(extractedUrl, "Поле url = Null")
        assertTrue(extractedUrl.isNotEmpty(), "Поле url пустое")

        assertNotNull(extractedId, "Поле id = Null")
        assertTrue(extractedId.isNotEmpty(), "Поле id пустое")

        open(extractedUrl)

        // Находим элементы на странице и вводим данные
        val elementCardNumber: SelenideElement = `$`("input[autocomplete='cc-number']").setValue("2202205000012424")// Карта без ACS кода
        elementCardNumber.should(Condition.exist)
        elementCardNumber.click()

        val elementCardName: SelenideElement = `$`("input[autocomplete='cc-name']").setValue("zxc zxc")// Имя
        elementCardName.should(Condition.exist)
        elementCardName.click()

        val elementCardExpiration: SelenideElement = `$`("input[autocomplete='cc-exp']").setValue("0535")// Срок
        elementCardExpiration.should(Condition.exist)
        elementCardExpiration.click()

        val elementCardCVV: SelenideElement = `$`(".inputs_back_cvv input").setValue("669")// CVV
        elementCardCVV.should(Condition.exist)
        elementCardCVV.click()

        val elementButtonSubmit: SelenideElement = `$`(".footer_buttons button")
        elementButtonSubmit.should(Condition.exist)
        elementButtonSubmit.click()

        checkTransactionStatus(extractedId, 3, "Холдирование", "Проверка статуса (холдирование)")

        val basePathForPut = "/api/v1/payment/confirm"
        val modifiedRequestBodyPut = sberGateBodyRequestForConfirmPutRequestGenerator.baseRequest().copy(
            amount = 2,
            id = extractedId.toIntOrNull()
        )
        val responsePut: Response = apiClient.put(
            path = basePathForPut,
            body = modifiedRequestBodyPut
        )
        responsePut.then()
            .statusCode(200)
            .body("success", equalTo(true))

        checkTransactionStatus(extractedId, 5, "Списание успешно", "Проверка статуса (Списание успешно)")

        val basePathForPutDouble = "/api/v1/payment/confirm"
        val modifiedRequestBodyPutDouble = sberGateBodyRequestForConfirmPutRequestGenerator.baseRequest().copy(
            amount = 2,
            id = extractedId.toIntOrNull()
        )
        val responsePutDouble: Response = apiClient.put(
            path = basePathForPutDouble,
            body = modifiedRequestBodyPutDouble
        )
        responsePutDouble.then()
            .statusCode(200)
            .body("success", equalTo(true))

        checkTransactionStatus(extractedId, 5, "Списание успешно", "Проверка статуса (Списание успешно)")

    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("e99a51e6-2bfa-4ba6-9ebf-e2b40d20f02a")
    @DisplayName("404. Подтверждение транзакций, находящихся в статусе Новая. Картой СГ")
    fun errorConfirmSberGate() {
        val basePath = "/api/v1/payment/pay/d96d0e7f-771a-4c85-9f13-5eda4bca9251"
        val requestBody = SberGateRequestGenerator.baseRequest()
        val response: Response = apiClient.post(
            path = basePath,
            body = requestBody
        )
        response.then()
            .statusCode(202)
            .body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath(
                    "pay.payment.jsonschema/POST_api_v1_payment_pay_method_uuid_SberGate.json"
                )
            )
            .extract()
            .jsonPath()

        val jsonPathPost = getJsonPath(response)
        val extractedId = jsonPathPost.getString("id")

        println("Извлечённый ID: $extractedId")

        val basePathForPut = "/api/v1/payment/confirm"
        val modifiedRequestBodyPut = sberGateBodyRequestForConfirmPutRequestGenerator.baseRequest().copy(
            amount = 2,
            id = extractedId.toIntOrNull()
        )
        val responsePut: Response = apiClient.put(
            path = basePathForPut,
            body = modifiedRequestBodyPut
        )
        responsePut.then()
            .statusCode(404)
            .body("error_message", equalTo("Транзакция не найдена"))
            .body("error_code", equalTo("S0.000011"))
            .body("type_error", equalTo("NOT_FOUND"))

    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("96c916ae-3ef2-4fa7-886e-1fb142759eb4")
    @DisplayName("422. Подтверждение платежа с передачей amount=0 в запросе Картой СГ")
    fun errorConfirmAmountZeroSberGate() {
        val basePath = "/api/v1/payment/pay/d96d0e7f-771a-4c85-9f13-5eda4bca9251"
        val requestBody = SberGateRequestGenerator.baseRequest()
        val response: Response = apiClient.post(
            path = basePath,
            body = requestBody
        )
        response.then()
            .statusCode(202)
            .body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath(
                    "pay.payment.jsonschema/POST_api_v1_payment_pay_method_uuid_SberGate.json"
                )
            )
            .extract()
            .jsonPath()

        val jsonPathPost = getJsonPath(response)
        val extractedUrl = jsonPathPost.getString("url")
        val extractedId = jsonPathPost.getString("id")

        println("Извлечённый URL: $extractedUrl")
        println("Извлечённый ID: $extractedId")

        assertNotNull(extractedUrl, "Поле url = Null")
        assertTrue(extractedUrl.isNotEmpty(), "Поле url пустое")

        assertNotNull(extractedId, "Поле id = Null")
        assertTrue(extractedId.isNotEmpty(), "Поле id пустое")

        open(extractedUrl)

        // Находим элементы на странице и вводим данные
        val elementCardNumber: SelenideElement = `$`("input[autocomplete='cc-number']").setValue("2202205000012424")// Карта без ACS кода
        elementCardNumber.should(Condition.exist)
        elementCardNumber.click()

        val elementCardName: SelenideElement = `$`("input[autocomplete='cc-name']").setValue("zxc zxc")// Имя
        elementCardName.should(Condition.exist)
        elementCardName.click()

        val elementCardExpiration: SelenideElement = `$`("input[autocomplete='cc-exp']").setValue("0535")// Срок
        elementCardExpiration.should(Condition.exist)
        elementCardExpiration.click()

        val elementCardCVV: SelenideElement = `$`(".inputs_back_cvv input").setValue("669")// CVV
        elementCardCVV.should(Condition.exist)
        elementCardCVV.click()

        val elementButtonSubmit: SelenideElement = `$`(".footer_buttons button")
        elementButtonSubmit.should(Condition.exist)
        elementButtonSubmit.click()

        checkTransactionStatus(extractedId, 3, "Холдирование", "Проверка статуса (холдирование)")

        val basePathForPut = "/api/v1/payment/confirm"
        val modifiedRequestBodyPut = sberGateBodyRequestForConfirmPutRequestGenerator.baseRequest().copy(
            amount = 0,
            id = extractedId.toIntOrNull()
        )
        val responsePut: Response = apiClient.put(
            path = basePathForPut,
            body = modifiedRequestBodyPut
        )
        responsePut.then()
            .statusCode(422)
            .body("error_code", equalTo(422))
            .body("error_message", equalTo("Не верные переданные данные в апи"))
            .body("type_error", equalTo("UNPROCESSABLE_CONTENT"))
            .body("errors.amount[0]", equalTo("Поле amount должно быть не меньше 0.01."))

    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("b7877b2b-c8bd-4824-b34f-07c0675be5c3")
    @DisplayName("424. Подтверждение платежа с передачей amount>суммы заказа в запросе Картой СГ")
    fun errorConfirmAmountMoreThanOrderSberGate() {
        val basePath = "/api/v1/payment/pay/d96d0e7f-771a-4c85-9f13-5eda4bca9251"
        val requestBody = SberGateRequestGenerator.baseRequest()
        val response: Response = apiClient.post(
            path = basePath,
            body = requestBody
        )
        response.then()
            .statusCode(202)
            .body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath(
                    "pay.payment.jsonschema/POST_api_v1_payment_pay_method_uuid_SberGate.json"
                )
            )
            .extract()
            .jsonPath()

        val jsonPathPost = getJsonPath(response)
        val extractedUrl = jsonPathPost.getString("url")
        val extractedId = jsonPathPost.getString("id")

        println("Извлечённый URL: $extractedUrl")
        println("Извлечённый ID: $extractedId")

        assertNotNull(extractedUrl, "Поле url = Null")
        assertTrue(extractedUrl.isNotEmpty(), "Поле url пустое")

        assertNotNull(extractedId, "Поле id = Null")
        assertTrue(extractedId.isNotEmpty(), "Поле id пустое")

        open(extractedUrl)

        // Находим элементы на странице и вводим данные
        val elementCardNumber: SelenideElement = `$`("input[autocomplete='cc-number']").setValue("2202205000012424")// Карта без ACS кода
        elementCardNumber.should(Condition.exist)
        elementCardNumber.click()

        val elementCardName: SelenideElement = `$`("input[autocomplete='cc-name']").setValue("zxc zxc")// Имя
        elementCardName.should(Condition.exist)
        elementCardName.click()

        val elementCardExpiration: SelenideElement = `$`("input[autocomplete='cc-exp']").setValue("0535")// Срок
        elementCardExpiration.should(Condition.exist)
        elementCardExpiration.click()

        val elementCardCVV: SelenideElement = `$`(".inputs_back_cvv input").setValue("669")// CVV
        elementCardCVV.should(Condition.exist)
        elementCardCVV.click()

        val elementButtonSubmit: SelenideElement = `$`(".footer_buttons button")
        elementButtonSubmit.should(Condition.exist)
        elementButtonSubmit.click()

        checkTransactionStatus(extractedId, 3, "Холдирование", "Проверка статуса (холдирование)")

        val basePathForPut = "/api/v1/payment/confirm"
        val modifiedRequestBodyPut = sberGateBodyRequestForConfirmPutRequestGenerator.baseRequest().copy(
            amount = 5,
            id = extractedId.toIntOrNull()
        )
        val responsePut: Response = apiClient.put(
            path = basePathForPut,
            body = modifiedRequestBodyPut
        )
        responsePut.then()
            .statusCode(424)
            .body("error_message", equalTo("При оплате возникла ошибка. Нет связи с вашим банком. Повторите попытку оплаты другой картой. (0029)"))
            .body("error_code", equalTo("S0.000029"))
            .body("type_error", equalTo("EXTERNAL_ERROR"))
            .body("transaction_id", equalTo(extractedId.toIntOrNull()))
            .body("error_description", equalTo("Сумма завершения превышает сумму холдирования"))

    }

}
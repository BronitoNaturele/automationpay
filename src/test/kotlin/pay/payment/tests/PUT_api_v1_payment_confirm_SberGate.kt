package pay.payment.tests

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide.*
import com.codeborne.selenide.SelenideElement
import io.restassured.module.jsv.JsonSchemaValidator
import io.restassured.path.json.JsonPath
import io.restassured.response.Response

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import pay.xprojectdata.client.ApiClient
import pay.xprojectdata.config.EnvironmentConfig
import pay.xprojectdata.dto.request.SberGateRequestGenerator
import pay.xprojectdata.dto.request.sberGateBodyRequestForConfirmPutRequestGenerator
import kotlin.test.assertNotNull
import java.time.Duration
import java.lang.Thread.sleep
import org.junit.jupiter.api.Assertions.assertEquals

class PUT_api_v1_payment_confirm_SberGate {
    private lateinit var apiClient: ApiClient

    // Валидация Json
    private fun getJsonPath(response: Response): JsonPath {
        val json = response.asString()
        return JsonPath.from(json)
    }

    @BeforeEach
    fun setUp() {
        val config = EnvironmentConfig.getConfigFromEnvVar()
        apiClient = ApiClient(config)

        //ApiLogger.enableLogging(logBody = true) // Включаем полное логирование
    }
    @AfterEach
    fun tearDown() {
        //ApiLogger.disableLogging()

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
            body = requestBody,
            headers = emptyMap() // если нужны дополнительные заголовки — передайте их
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
        val elementCardNumber: SelenideElement = `$`("input[autocomplete='cc-number']").setValue("2202205000012424")//Карта без ACS кода
        elementCardNumber.should(Condition.exist)
        elementCardNumber.click()

        val elementCardName: SelenideElement = `$`("input[autocomplete='cc-name']").setValue("zxc zxc")
        elementCardName.should(Condition.exist)
        elementCardName.click()

        val elementCardExpiration: SelenideElement = `$`("input[autocomplete='cc-exp']").setValue("0535")
        elementCardExpiration.should(Condition.exist)
        elementCardExpiration.click()

        val elementCardCVV: SelenideElement = `$`(".inputs_back_cvv input").setValue("669")
        elementCardCVV.should(Condition.exist)
        elementCardCVV.click()

        val elementButtonSubmit: SelenideElement = `$`(".footer_buttons button")
        elementButtonSubmit.should(Condition.exist)
        elementButtonSubmit.click()

        // Задержка по секундам
        val durationMillis = Duration.ofSeconds(20).toMillis()
        try {
            sleep(durationMillis)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        //Проверяем статус транзакции
        val basePathForCheckTransactionStatus = "/api/v1/payment/status/" + extractedId.toIntOrNull()
        val responseGetCheckTransaction: Response = apiClient.get(
            path = basePathForCheckTransactionStatus,
            headers = emptyMap()
        )

        // Валидация ответа
        responseGetCheckTransaction.then()
            .statusCode(200)
            .body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath(
                    "pay.payment.jsonschema/GET_api_v1_payment_status_id_SberGate.json"
                )
            )

        // Извлечение данных
        val jsonPathStatus = getJsonPath(responseGetCheckTransaction)
        val status = jsonPathStatus.getInt("status")
        val statusText = jsonPathStatus.getString("statusText")

        // Проверка значений полей
        assertEquals(3, status)
        assertEquals("Холдирование", statusText)

        //Подготовка тела PUT запроса на подтверждение платежа
        val basePathForPut = "/api/v1/payment/confirm"
        val modifiedRequestBodyPut = sberGateBodyRequestForConfirmPutRequestGenerator.baseRequest().copy(
            id = extractedId.toIntOrNull(),
        )

        //Выполнение PUT-запроса
        val responsePut: Response = apiClient.put(
            path = basePathForPut,
            body = modifiedRequestBodyPut,
            headers = emptyMap() // если нужны дополнительные заголовки — передайте их
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

        //Тут надо будет дописать(когда будет готово) запрос на проверку статуса транзакции
    }
}
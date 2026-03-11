package pay.payment.tests

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import configLogger
import io.restassured.RestAssured
import io.restassured.module.jsv.JsonSchemaValidator
import io.restassured.path.json.JsonPath
import io.restassured.response.Response
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasItem
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import pay.xprojectdata.client.ApiClient
import pay.xprojectdata.config.EnvironmentConfig
import pay.xprojectdata.dto.request.*
import pay.xprojectdata.dto.response.errorNoMethodPayMethodUuidResponseBodySberGate
import pay.xprojectdata.dto.response.errorPayMethodUuidResponseBodySberGate
import ru.testit.annotations.WorkItemIds
import java.util.stream.Stream

class POSTApiV1PaymentPayMethodUuidSberGate {
    private lateinit var apiClient: ApiClient
    private val mapper = jacksonObjectMapper()
    val basePath = "/api/v1/payment/pay/d96d0e7f-771a-4c85-9f13-5eda4bca9251"

    companion object {
        @BeforeAll
        @JvmStatic
        fun globalSetup() {
            configLogger.globalSetup()
        }

        @JvmStatic
        fun provideInvalidAmountValues(): Stream<Arguments> = Stream.of(
            Arguments.of("1a!", "Строка с символами", "Поле amount должно быть числом."),
            Arguments.of(mapOf<String, Any>(), "Пустой объект", "Поле amount обязательно для заполнения."),
            Arguments.of(listOf<Any>(), "Пустой массив", "Поле amount обязательно для заполнения."),
            Arguments.of(false, "Булево значение", "Поле amount должно быть числом.")
        )

        @JvmStatic
        fun provideInvalidInvoiceIdValues(): Stream<Arguments> = Stream.of(
            Arguments.of(2, "Целое число", "Поле invoice id должно быть строкой."),
            Arguments.of(mapOf<String, Any>(), "Пустой объект", "Поле invoice id обязательно для заполнения."),
            Arguments.of(listOf<Any>(), "Пустой массив", "Поле invoice id обязательно для заполнения."),
            Arguments.of(false, "Булево значение", "Поле invoice id должно быть строкой.")
        )

        @JvmStatic
        fun provideInvalidMobileValues(): Stream<Arguments> = Stream.of(
            Arguments.of(2, "Целое число", "Поле Моб. номер должно иметь значение логического типа."),
            Arguments.of("1a!", "Строка", "Поле Моб. номер должно иметь значение логического типа."),
            Arguments.of(mapOf<String, Any>(), "Пустой объект", "Поле Моб. номер обязательно для заполнения."),
            Arguments.of(listOf<Any>(), "Пустой массив", "Поле Моб. номер обязательно для заполнения.")
        )

        @JvmStatic
        fun provideInvalidPlatformValues(): Stream<Arguments> = Stream.of(
            Arguments.of(2, "Целое число", "Поле platform должно быть строкой.","Выбранное значение для platform ошибочно."),
            Arguments.of(mapOf<String, Any>(), "Пустой объект", "Поле platform обязательно для заполнения."),
            Arguments.of(listOf<Any>(), "Пустой массив", "Поле platform обязательно для заполнения."),
            Arguments.of(false, "Булево значение", "Поле platform должно быть строкой.", "Выбранное значение для platform ошибочно.")
        )

        @JvmStatic
        fun provideInvalidAccountIdValues(): Stream<Arguments> = Stream.of(
            Arguments.of(2, "Целое число", "Поле account id должно быть строкой."),
            Arguments.of(mapOf<String, Any>(), "Пустой объект", "Поле account id должно быть строкой."),
            Arguments.of(listOf<Any>(), "Пустой массив", "Поле account id должно быть строкой.", "Поле account id имеет ошибочный формат."),
            Arguments.of(false, "Булево значение", "Поле account id должно быть строкой.")
        )

        @JvmStatic
        fun provideInvalidNameValues(): Stream<Arguments> = Stream.of(
            Arguments.of(2, "Целое число", "Поле Имя должно быть строкой."),
            Arguments.of(mapOf<String, Any>(), "Пустой объект", "Поле Имя должно быть строкой."),
            Arguments.of(listOf<Any>(), "Пустой массив", "Поле Имя должно быть строкой.", "Поле account id имеет ошибочный формат."),
            Arguments.of(false, "Булево значение", "Поле Имя должно быть строкой.")
        )

        @JvmStatic
        fun provideInvalidPayloadValues(): Stream<Arguments> = Stream.of(
            Arguments.of(2, "Целое число", "Поле payload должно быть массивом."),
            Arguments.of("1a!", "Строка", "Поле payload должно быть массивом."),
            Arguments.of(false, "Булево значение", "Поле payload должно быть массивом.")
        )

        @JvmStatic
        fun provideInvalidPayloadKeyValues(): Stream<Arguments> = Stream.of(
            Arguments.of(2, "Целое число", "Поле payload.0.key должно быть строкой."),
            Arguments.of(mapOf<String, Any>(), "Пустой объект", "Поле payload.0.key обязательно для заполнения."),
            Arguments.of(listOf<Any>(), "Пустой массив", "Поле payload.0.key обязательно для заполнения."),
            Arguments.of(false, "Булево значение", "Поле payload.0.key должно быть строкой.")
        )

        @JvmStatic
        fun provideInvalidPayloadValueValues(): Stream<Arguments> = Stream.of(
            Arguments.of(2, "Целое число", "Поле payload.0.value должно быть строкой."),
            Arguments.of(mapOf<String, Any>(), "Пустой объект", "Поле payload.0.value должно быть строкой."),
            Arguments.of(listOf<Any>(), "Пустой массив", "Поле payload.0.value должно быть строкой."),
            Arguments.of(false, "Булево значение", "Поле payload.0.value должно быть строкой.")
        )

        @JvmStatic
        fun provideInvalidPhoneValues(): Stream<Arguments> = Stream.of(
            Arguments.of(2, "Целое число", "Поле Телефон должно быть строкой.", "Поле Телефон имеет ошибочный формат"),
            Arguments.of(mapOf<String, Any>(), "Пустой объект", "Поле Телефон должно быть строкой.", "Поле Телефон имеет ошибочный формат."),
            Arguments.of(listOf<Any>(), "Пустой массив", "Поле Телефон должно быть строкой.", "Поле Телефон имеет ошибочный формат."),
            Arguments.of(false, "Булево значение", "Поле Телефон должно быть строкой.", "Поле Телефон имеет ошибочный формат.")
        )

        @JvmStatic
        fun provideInvalidEmailValues(): Stream<Arguments> = Stream.of(
            Arguments.of(2, "Целое число", "Поле E-Mail адрес должно быть строкой."),
            Arguments.of(mapOf<String, Any>(), "Пустой объект", "Поле E-Mail адрес должно быть строкой."),
            Arguments.of(listOf<Any>(), "Пустой массив", "Поле E-Mail адрес должно быть строкой."),
            Arguments.of(false, "Булево значение", "Поле E-Mail адрес должно быть строкой.")
        )

        @JvmStatic
        fun provideInvalidDescriptionValues(): Stream<Arguments> = Stream.of(
            Arguments.of(2, "Целое число", "Поле Описание должно быть строкой."),
            Arguments.of(mapOf<String, Any>(), "Пустой объект", "Поле Описание должно быть строкой."),
            Arguments.of(listOf<Any>(), "Пустой массив", "Поле Описание должно быть строкой."),
            Arguments.of(false, "Булево значение", "Поле Описание должно быть строкой.")
        )

        @JvmStatic
        fun provideInvalidFieldsValues(): Stream<Arguments> = Stream.of(
            Arguments.of(2, "Целое число", "Поле fields должно быть массивом."),
            Arguments.of("1a!", "Строка", "Поле fields должно быть массивом."),
            Arguments.of(false, "Булево значение", "Поле fields должно быть массивом.")
        )

        @JvmStatic
        fun provideInvalidFieldsSuccessUrlValues(): Stream<Arguments> = Stream.of(
            Arguments.of(2, "Целое число", "Поле fields.success url должно быть строкой."),
            Arguments.of(mapOf<String, Any>(), "Пустой объект", "Поле fields.success url должно быть строкой."),
            Arguments.of(listOf<Any>(), "Пустой массив", "Поле fields.success url должно быть строкой."),
            Arguments.of(false, "Булево значение", "Поле fields.success url должно быть строкой.")
        )

        @JvmStatic
        fun provideInvalidFieldsErrorUrlValues(): Stream<Arguments> = Stream.of(
            Arguments.of(2, "Целое число", "Поле fields.error url должно быть строкой."),
            Arguments.of(mapOf<String, Any>(), "Пустой объект", "Поле fields.error url должно быть строкой."),
            Arguments.of(listOf<Any>(), "Пустой массив", "Поле fields.error url должно быть строкой."),
            Arguments.of(false, "Булево значение", "Поле fields.error url должно быть строкой.")
        )

        @JvmStatic
        fun provideInvalidFieldsTtlValues(): Stream<Arguments> = Stream.of(
            Arguments.of(1.1, "Число с плавающей точкой", "Поле fields.ttl должно быть целым числом.","fields.ttl less than the minimum value."),
            Arguments.of("1a!", "Строка", "Поле fields.ttl должно быть целым числом."),
            Arguments.of(mapOf<String, Any>(), "Пустой объект", "Поле fields.ttl должно быть целым числом.", "fields.ttl is greater than the maximum allowed."),
            Arguments.of(listOf<Any>(), "Пустой массив", "Поле fields.ttl должно быть целым числом.", "fields.ttl is greater than the maximum allowed."),
            Arguments.of(false, "Булево значение", "Поле fields.ttl должно быть целым числом.", "fields.ttl less than the minimum value.")
        )
    }

    @BeforeEach
    fun setUp() {
        val config = EnvironmentConfig.getConfigFromEnvVar()
        apiClient = ApiClient(config)
        //ApiLogger.enableLogging(logBody = true)
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

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("e3faba9c-6dff-432a-946d-6eab13fdea57")
    @DisplayName("Успешное выполнение запроса. 202.Проверка валидации JSON схемы ответа")
    fun validatingTheJsonSchemeToTheResponseWithMethodUuid() {
        //Подготовка тела запроса
        val requestBody = SberGateRequestGenerator.baseRequest()
        //Выполнение POST-запроса
        val response: Response = apiClient.post(
            path = basePath,
            body = requestBody,
            headers = emptyMap() // если нужны дополнительные заголовки — передайте их
        )

        //Валидация ответа (единая цепочка)
        response.then()
            .statusCode(202) // Проверяем статус-код
            .body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath(
                    "pay.payment.jsonschema/POST_api_v1_payment_pay_method_uuid_SberGate.json"
                )
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("ca652d14-a6be-47f3-8bd7-688804ae0605")
    @DisplayName("Ошибка авторизации. 401. Отсутствует токен")
    fun noTokenSberGate() {
        val response = RestAssured
            .given()
            .post("/api/v1/payment/pay/d96d0e7f-771a-4c85-9f13-5eda4bca9251")

        // Валидируем статус
        response.then().statusCode(401)

        // Парсим в DTO
        val paymentResponse: errorPayMethodUuidResponseBodySberGate = mapper.readValue(
            response.asString(),
            errorPayMethodUuidResponseBodySberGate::class.java
        )

        // Проверяем поля с помощью JUnit assertions
        assertEquals(
            "Ошибка авторизации или данная операция запрещена правами доступа",
            paymentResponse.error_message,
            "Поле error_message должно содержать сообщение об ошибке авторизации"
        )
        assertEquals(
            "S0.000002",
            paymentResponse.error_code,
            "Поле error_code должно быть равно S0.000002"
        )
        assertEquals(
            "UNAUTHORIZED",
            paymentResponse.type_error,
            "Поле type_error должно быть равно UNAUTHORIZED"
        )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("ca652d14-a6be-47f3-8bd7-688804ae0605")
    @DisplayName("Ошибка авторизации. 401. Токен не валиден")
    fun noValidTokenSberGate() {
        val requestBody = SberGateRequestGenerator.baseRequest()
        val response = RestAssured
            .given()
            .body(requestBody)
            .header("Authorization", "Bearer e26ABDDy9HTV0gFoX1uCwdld9uSSjEYlrV7v0qrs2OfZOONm223XLMLKasdasd9GyPDMJFpmMIQLSPkG9XfCzT")
            .post("/api/v1/payment/pay/d96d0e7f-771a-4c85-9f13-5eda4bca9251")

        // Валидируем статус
        response.then().statusCode(401)

        // Парсим в DTO
        val paymentResponse: errorPayMethodUuidResponseBodySberGate = mapper.readValue(
            response.asString(),
            errorPayMethodUuidResponseBodySberGate::class.java
        )

        // Проверяем поля с помощью JUnit assertions
        assertEquals(
            "Ошибка авторизации или данная операция запрещена правами доступа",
            paymentResponse.error_message,
            "Поле error_message должно содержать сообщение об ошибке авторизации"
        )
        assertEquals(
            "S0.000002",
            paymentResponse.error_code,
            "Поле error_code должно быть равно S0.000002"
        )
        assertEquals(
            "UNAUTHORIZED",
            paymentResponse.type_error,
            "Поле type_error должно быть равно UNAUTHORIZED"
        )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("f4c9eb2f-8270-40fa-8be1-19fd697ba794")
    @DisplayName("Не валидный method_uuid в url. 406")
    fun notValidMethodUuidSberGate() {
        val requestBody = SberGateRequestGenerator.baseRequest()
        //Выполнение POST-запроса
        val response: Response = apiClient.post(
            path = "/api/v1/payment/pay/123",
            body = requestBody,
            headers = emptyMap() // если нужны дополнительные заголовки — передайте их
        )
        // Валидируем статус
        response.then().statusCode(406)

        // Парсим в DTO
        val paymentResponse: errorPayMethodUuidResponseBodySberGate = mapper.readValue(
            response.asString(),
            errorPayMethodUuidResponseBodySberGate::class.java
        )

        // Проверяем поля с помощью JUnit assertions
        assertEquals(
            "Данный метод недоступен для платежа",
            paymentResponse.error_message,
            "Поле error_message должно содержать сообщение об ошибке авторизации"
        )
        assertEquals(
            "S0.000005",
            paymentResponse.error_code,
            "Поле error_code должно быть равно S0.000005"
        )
        assertEquals(
            "NOT_ACCEPTABLE",
            paymentResponse.type_error,
            "Поле type_error должно быть равно NOT_ACCEPTABLE"
        )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("9a4b67b2-7661-420f-afcf-dd291cf18944")
    @DisplayName("Не передан method_uuid в url. 405")
    fun noMethodUuidSberGate() {
        val requestBody = SberGateRequestGenerator.baseRequest()
        //Выполнение POST-запроса
        val response: Response = apiClient.post(
            path = "/api/v1/payment/pay/",
            body = requestBody,
            headers = emptyMap() // если нужны дополнительные заголовки — передайте их
        )
        // Валидируем статус
        response.then().statusCode(405)

        // Парсим в DTO
        val paymentResponse: errorNoMethodPayMethodUuidResponseBodySberGate = mapper.readValue(
            response.asString(),
            errorNoMethodPayMethodUuidResponseBodySberGate::class.java
        )

        // Проверяем поля с помощью JUnit assertions
        assertEquals(
            "The POST method is not supported for route api/v1/payment/pay. Supported methods: GET, HEAD.",
            paymentResponse.error_message,
            "Поле error_message должно содержать сообщение об ошибке авторизации"
        )
        assertEquals(
            "405",
            paymentResponse.error_code,
            "Поле error_code должно быть равно 405"
        )
        assertEquals(
            "METHOD_NOT_ALLOWED",
            paymentResponse.type_error,
            "Поле type_error должно быть равно METHOD_NOT_ALLOWED"
        )
    }

    // Тут надо будет добавить проверку через ручку админки, когда будет готова
    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("9e3222a9-f266-4743-8d4b-8af1ebb52a06")
    @DisplayName("202. Повторное проведение платежа с тем же invoice_id пока не вышел таймаут создания заказа.")
    fun repeatInvoiceIdSuccess() {
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy()
        // Сохраняем invoice_id из первого запроса
        val firstInvoiceId = modifiedRequest.invoice_id
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )

        response.then()
            .statusCode(202)
            .body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath(
                    "pay.payment.jsonschema/POST_api_v1_payment_pay_method_uuid_SberGate.json"
                )
            )

        val modifiedRequestRepeat = SberGateRequestGenerator.baseRequest().copy(
            invoice_id = firstInvoiceId
        )
        val responseRepeat: Response = apiClient.post(
            path = basePath,
            body = modifiedRequestRepeat
        )

        responseRepeat.then().log().all()
            .statusCode(202)
            .body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath(
                    "pay.payment.jsonschema/POST_api_v1_payment_pay_method_uuid_SberGate.json"
                )
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("490c2844-035e-4f54-9202-9477f3a04168")
    @DisplayName("423. Повторное проведение платежа с тем же invoice_id когда вышел таймаут создания заказа.")
    fun repeatInvoiceIderror() {
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            invoice_id = "123456789"
        )

        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest,
            headers = emptyMap()
        )

        //Валидация ответа (единая цепочка)
        response.then()
            .statusCode(423)
            .body(
                "error_message", equalTo("Транзакция уже обработана"),
                "error_code", equalTo("S0.000013"),
                "type_error", equalTo("LOCKED")
                )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("e3faba9c-6dff-432a-946d-6eab13fdea57")
    @DisplayName("202. Успешное выполнение запроса. platform = IOS, mobile = true")
    fun platformIosMobileTrue(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            platform = "IOS",
            mobile = true
        )

        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(202) // Проверяем статус-код
            .body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath(
                    "pay.payment.jsonschema/POST_api_v1_payment_pay_method_uuid_SberGate.json"
                )
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("e3faba9c-6dff-432a-946d-6eab13fdea57")
    @DisplayName("202. Успешное выполнение запроса. platform = IOS, mobile = false")
    fun platformIosMobileFalse(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            platform = "IOS",
            mobile = false
        )

        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(202) // Проверяем статус-код
            .body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath(
                    "pay.payment.jsonschema/POST_api_v1_payment_pay_method_uuid_SberGate.json"
                )
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("e3faba9c-6dff-432a-946d-6eab13fdea57")
    @DisplayName("202. Успешное выполнение запроса. platform = ANDROID, mobile = true")
    fun platformAndroidMobileTrue(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            platform = "ANDROID",
            mobile = true
        )

        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(202) // Проверяем статус-код
            .body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath(
                    "pay.payment.jsonschema/POST_api_v1_payment_pay_method_uuid_SberGate.json"
                )
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("e3faba9c-6dff-432a-946d-6eab13fdea57")
    @DisplayName("202. Успешное выполнение запроса. platform = ANDROID, mobile = false")
    fun platformAndroidMobileFalse(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            platform = "ANDROID",
            mobile = false
        )

        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(202) // Проверяем статус-код
            .body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath(
                    "pay.payment.jsonschema/POST_api_v1_payment_pay_method_uuid_SberGate.json"
                )
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("e3faba9c-6dff-432a-946d-6eab13fdea57")
    @DisplayName("202. Успешное выполнение запроса. platform = WINDOWS_PHONE, mobile = true")
    fun platformWindowsPhoneMobileTrue(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            platform = "WINDOWS_PHONE",
            mobile = true
        )

        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(202) // Проверяем статус-код
            .body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath(
                    "pay.payment.jsonschema/POST_api_v1_payment_pay_method_uuid_SberGate.json"
                )
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("e3faba9c-6dff-432a-946d-6eab13fdea57")
    @DisplayName("202. Успешное выполнение запроса. platform = WINDOWS_PHONE, mobile = false")
    fun platformWindowsPhoneMobileFalse(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            platform = "WINDOWS_PHONE",
            mobile = false
        )

        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(202) // Проверяем статус-код
            .body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath(
                    "pay.payment.jsonschema/POST_api_v1_payment_pay_method_uuid_SberGate.json"
                )
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("e3faba9c-6dff-432a-946d-6eab13fdea57")
    @DisplayName("202. Успешное выполнение запроса. platform = WEB, mobile = true")
    fun platformWebMobileTrue(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            platform = "WEB",
            mobile = true
        )

        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(202) // Проверяем статус-код
            .body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath(
                    "pay.payment.jsonschema/POST_api_v1_payment_pay_method_uuid_SberGate.json"
                )
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("e3faba9c-6dff-432a-946d-6eab13fdea57")
    @DisplayName("202. Успешное выполнение запроса. platform = WEB, mobile = false")
    fun platformWebMobileFalse(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            platform = "WEB",
            mobile = false
        )

        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(202) // Проверяем статус-код
            .body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath(
                    "pay.payment.jsonschema/POST_api_v1_payment_pay_method_uuid_SberGate.json"
                )
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("d82e7f5f-8608-4345-9e1e-bea206ebb6d3")
    @DisplayName("202. Проведение платежа гостевым аккаунтом")
    fun noAccountId(){
        val modifiedRequest = SberGateRequestGenerator.noAccountIdRequest().copy(
        )

        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .log().all()
            .statusCode(202) // Проверяем статус-код
            .body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath(
                    "pay.payment.jsonschema/POST_api_v1_payment_pay_method_uuid_SberGate.json"
                )
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("ab70782c-06b6-439c-bde4-e416e3228520")
    @DisplayName("Проверка обязательности полей. 400. no amount")
    fun noAmount(){
        val noAmountRequestBody = SberGateRequestGenerator.noAmountRequest().copy()
        val response: Response = apiClient.post(
            path = basePath,
            body = noAmountRequestBody
        )
        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.amount", hasItem("Поле amount обязательно для заполнения.")
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("ab70782c-06b6-439c-bde4-e416e3228520")
    @DisplayName("Проверка обязательности полей. 400. no mobile")
    fun noMobile(){
        val noMobileRequestBody = SberGateRequestGenerator.noMobileRequest().copy()
        val response: Response = apiClient.post(
            path = basePath,
            body = noMobileRequestBody
        )
        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.mobile", hasItem("Поле Моб. номер обязательно для заполнения.")
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("ab70782c-06b6-439c-bde4-e416e3228520")
    @DisplayName("Проверка обязательности полей. 400. no platform")
    fun noPlatform(){
        val noPlatformRequestBody = SberGateRequestGenerator.noPlatformRequest().copy()
        val response: Response = apiClient.post(
            path = basePath,
            body = noPlatformRequestBody
        )
        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.platform", hasItem("Поле platform обязательно для заполнения.")
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("ab70782c-06b6-439c-bde4-e416e3228520")
    @DisplayName("Проверка обязательности полей. 400. no invoice_id")
    fun noInvoiceId(){
        val noInvoiceIDRequestBody = SberGateRequestGenerator.noInvoiceIdRequest().copy()
        val response: Response = apiClient.post(
            path = basePath,
            body = noInvoiceIDRequestBody
        )
        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.invoice_id", hasItem("Поле invoice id обязательно для заполнения.")
            )
    }

    @ParameterizedTest
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. Для поля amount: Строка, объект, пустой массив, булево")
    @MethodSource("provideInvalidAmountValues")
    fun amountInvalidValuesTest(amountValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(amount = amountValue)
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )

        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.amount", hasItem(expectedErrorMessage)
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. amount = null")
    fun amountNull(){
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(amount = null)
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
            )
        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.amount", hasItem("Поле amount обязательно для заполнения.")
            )
    }

    @ParameterizedTest
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. Для поля invoice_id: Целое число, объект, пустой массив, булево")
    @MethodSource("provideInvalidInvoiceIdValues")
    fun invoiceIdInvalidValuesTest(invoiceIdValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(invoice_id = invoiceIdValue)
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )

        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.invoice_id", hasItem(expectedErrorMessage)
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. invoice_id = null")
    fun invoiceIdNull(){
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(invoice_id = null)
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.invoice_id", hasItem("Поле invoice id обязательно для заполнения.")
            )
    }

    @ParameterizedTest
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. Для поля mobile: Целое число, строка, объект, пустой массив")
    @MethodSource("provideInvalidMobileValues")
    fun mobileInvalidValuesTest(mobileValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(mobile = mobileValue)
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )

        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.mobile", hasItem(expectedErrorMessage)
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. mobile = null")
    fun mobileNull(){
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(mobile = null)
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.mobile", hasItem("Поле Моб. номер обязательно для заполнения.")
            )
    }

    @ParameterizedTest
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. Для поля platform: Целое число, объект, пустой массив, булево")
    @MethodSource("provideInvalidPlatformValues")
    fun platformInvalidValuesTest(platformValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(platform = platformValue)
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )

        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.platform", hasItem(expectedErrorMessage)
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. platform = null")
    fun platformNull(){
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(platform = null)
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.platform", hasItem("Поле platform обязательно для заполнения.")
            )
    }

    @ParameterizedTest
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. Для поля account_id: Целое число, объект, пустой массив, булево")
    @MethodSource("provideInvalidAccountIdValues")
    fun accountIdInvalidValuesTest(accountIdValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(account_id = accountIdValue)
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )

        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.account_id", hasItem(expectedErrorMessage)
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. account_id = null")
    fun accountIdNull(){
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(account_id = null)
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.account_id", hasItem("Поле account id должно быть строкой."),
                "errors.account_id", hasItem("Поле account id имеет ошибочный формат.")
            )
    }

    @ParameterizedTest
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. Для поля name: Целое число, объект, пустой массив, булево")
    @MethodSource("provideInvalidNameValues")
    fun nameInvalidValuesTest(nameValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(name = nameValue)
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )

        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.name", hasItem(expectedErrorMessage)
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. name = null")
    fun nameNull(){
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(name = null)
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.name", hasItem("Поле Имя должно быть строкой.")
            )
    }

    @ParameterizedTest
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. Для поля payload: Целое число, строка, булево")
    @MethodSource("provideInvalidPayloadValues")
    fun payloadInvalidValuesTest(payloadValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(payload = payloadValue)
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )

        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.payload", hasItem(expectedErrorMessage)
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. payload = null")
    fun payloadNull(){
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(payload = null)
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.payload", hasItem("Поле payload должно быть массивом.")
            )
    }

    @ParameterizedTest
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. Для поля payload.key: Целое число, объект, пустой массив, булево")
    @MethodSource("provideInvalidPayloadKeyValues")
    fun payloadKeyInvalidValuesTest(payloadKeyValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(
            payload = listOf(NullPayloadItem(
                key = payloadKeyValue,
                value = "testValue"
            )
          )
        )
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )

        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.'payload.0.key'", hasItem(expectedErrorMessage)
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. payload.key = null")
    fun payloadToKeyNull(){
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(
            payload = listOf(
                NullPayloadItem(
                    key = null,
                    value = "testValue"
            )
        )
        )
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then().log().all()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.'payload.0.key'", hasItem("Поле payload.0.key обязательно для заполнения.")
            )
    }

    @ParameterizedTest
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. Для поля payload.value: Целое число, объект, пустой массив, булево")
    @MethodSource("provideInvalidPayloadValueValues")
    fun payloadValueInvalidValuesTest(payloadValueValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(
            payload = listOf(NullPayloadItem(
                key = "testKey",
                value = payloadValueValue
            )
            )
        )
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )

        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.'payload.0.value'", hasItem(expectedErrorMessage)
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. payload.value = null")
    fun payloadToValueNull(){
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(
            payload = listOf(
                NullPayloadItem(
                    key = "testKey",
                    value = null
            )
        )
        )
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.'payload.0.value'", hasItem("Поле payload.0.value должно быть строкой.")
            )
    }

    @ParameterizedTest
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. Для поля phone: Целое число, объект, пустой массив, булево")
    @MethodSource("provideInvalidPhoneValues")
    fun phoneInvalidValuesTest(phoneValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(
            phone = phoneValue
            )
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )

        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.phone", hasItem(expectedErrorMessage)
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("a64573aa-05f7-490d-a2ea-08b2038511cf")
    @DisplayName("202. Проверка nullable полей. phone = null")
    fun phoneNull(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(phone = null)
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(202)
    }

    @ParameterizedTest
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. Для поля email: Целое число, объект, пустой массив, булево")
    @MethodSource("provideInvalidEmailValues")
    fun emailInvalidValuesTest(emailValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(
            email = emailValue
        )
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )

        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.email", hasItem(expectedErrorMessage)
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. email = null")
    fun emailNull(){
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(email = null)
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.email", hasItem("Поле E-Mail адрес должно быть строкой.")
            )
    }

    @ParameterizedTest
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. Для поля description: Целое число, объект, пустой массив, булево")
    @MethodSource("provideInvalidDescriptionValues")
    fun descriptionInvalidValuesTest(descriptionValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(
            description = descriptionValue
        )
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )

        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.description", hasItem(expectedErrorMessage)
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. description = null")
    fun descriptionNull(){
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(description = null)
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.description", hasItem("Поле Описание должно быть строкой.")
            )
    }

    @ParameterizedTest
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. Для поля fields: Целое число, строка, булево")
    @MethodSource("provideInvalidFieldsValues")
    fun fieldsInvalidValuesTest(fieldsValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(
            fields = fieldsValue
        )
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )

        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.fields", hasItem(expectedErrorMessage)
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. fields = null")
    fun fieldsNull(){
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(fields = null)
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.fields", hasItem("Поле fields должно быть массивом.")
            )
    }

    @ParameterizedTest
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. Для поля fields.successUrl: Целое число, объект, пустой массив, булево")
    @MethodSource("provideInvalidFieldsSuccessUrlValues")
    fun fieldsSuccessUrlInvalidValuesTest(fieldsSuccessUrlValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(
            fields = NullFields(
                    successUrl = fieldsSuccessUrlValue,
                    errorUrl = "https://uat-pay.av.ru//error/123456",
                    ttl = 3600
                )
            )

        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )

        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.'fields.successUrl'", hasItem(expectedErrorMessage)
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. fields.successUrl = null")
    fun fieldsToSuccessUrlNull(){
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(
            fields = NullFields(
                successUrl = null,
                errorUrl = "https://uat-pay.av.ru//error/123456",
                ttl = 3600
            )
        )
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors[\"fields.successUrl\"]", hasItem("Поле fields.success url должно быть строкой.")
            )
    }

    @ParameterizedTest
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. Для поля fields.errorUrl: Целое число, объект, пустой массив, булево")
    @MethodSource("provideInvalidFieldsErrorUrlValues")
    fun fieldsErrorUrlInvalidValuesTest(fieldsErrorUrlValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(
            fields = NullFields(
                successUrl = "https://uat-pay.av.ru/success/123456",
                errorUrl = fieldsErrorUrlValue,
                ttl = 3600
            )
        )

        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )

        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.'fields.errorUrl'", hasItem(expectedErrorMessage)
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. fields.errorUrl = null")
    fun fieldsToErrorUrlNull(){
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(
            fields = NullFields(
                successUrl = "https://uat-pay.av.ru/success/123456",
                errorUrl = null,
                ttl = 3600
            )
        )
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors[\"fields.errorUrl\"]", hasItem("Поле fields.error url должно быть строкой.")
            )
    }

    @ParameterizedTest
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. Для поля fields.ttl: Число с плавающей точкой, строка, объект, пустой массив, булево")
    @MethodSource("provideInvalidFieldsTtlValues")
    fun fieldsTtlInvalidValuesTest(fieldsTtlValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(
            fields = NullFields(
                successUrl = "https://uat-pay.av.ru/success/123456",
                errorUrl = "https://uat-pay.av.ru//error/123456",
                ttl = fieldsTtlValue
            )
        )

        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )

        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.'fields.ttl'", hasItem(expectedErrorMessage)
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("21a51eeb-112b-4044-abae-6eaa3b48d36b")
    @DisplayName("Валидации по типу данных. 400. fields.ttl = null")
    fun fieldsToTtlNull(){
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(
            fields = NullFields(
                successUrl = "https://uat-pay.av.ru/success/123456",
                errorUrl = "https://uat-pay.av.ru//error/123456",
                ttl = null
            )
        )
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors[\"fields.ttl\"]", hasItem("Поле fields.ttl должно быть целым числом."),
                "errors[\"fields.ttl\"]", hasItem("fields.ttl less than the minimum value.")
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("1e132b2c-e84b-45af-b282-313bd1d2736a")
    @DisplayName("Другие валидации. 400. invoice_id = 21 symbol")
    fun invoiceIdManySymbols(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            invoice_id = "123456789012345678901"
        )
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.invoice_id", hasItem("Количество символов в поле invoice id не может превышать 20.")
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("1e132b2c-e84b-45af-b282-313bd1d2736a")
    @DisplayName("Другие валидации. 400. email без @")
    fun emailWithoutDog(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            email = "klepa_email.ru"
        )
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.mail", hasItem("поправить после фикса бага")
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("1e132b2c-e84b-45af-b282-313bd1d2736a")
    @DisplayName("Другие валидации. 400. email с пробелом")
    fun emailHaveAWhitespace(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            email = "klepa_e@ mail.ru"
        )
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.mail", hasItem("поправить после фикса бага")
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("1e132b2c-e84b-45af-b282-313bd1d2736a")
    @DisplayName("Другие валидации. 400. email с двумя точками")
    fun emailWithDoublePoints(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            email = "klepa_e@mail..ru"
        )
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.mail", hasItem("поправить после фикса бага")
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("1e132b2c-e84b-45af-b282-313bd1d2736a")
    @DisplayName("Другие валидации. 400. phone с пробелом")
    fun phoneHaveAWhitespace(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            phone = "7900 0000000"
        )
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.phone", hasItem("Поле Телефон имеет ошибочный формат.")
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("1e132b2c-e84b-45af-b282-313bd1d2736a")
    @DisplayName("Другие валидации. 400. phone начинается с 9")
    fun phoneStartAtNine(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            phone = "99000000000"
        )
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.phone", hasItem("Поле Телефон имеет ошибочный формат.")
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("1e132b2c-e84b-45af-b282-313bd1d2736a")
    @DisplayName("Другие валидации. 400. phone начинается с 6")
    fun phoneStartAtSix(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            phone = "69000000000"
        )
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.phone", hasItem("Поле Телефон имеет ошибочный формат.")
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("1e132b2c-e84b-45af-b282-313bd1d2736a")
    @DisplayName("Другие валидации. 400. phone начинается с 7 + 11 цифр после")
    fun phoneStartAtSevenPlusElevenNumbers(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            phone = "790000000000"
        )
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.phone", hasItem("Поле Телефон имеет ошибочный формат.")
            )
    }
}
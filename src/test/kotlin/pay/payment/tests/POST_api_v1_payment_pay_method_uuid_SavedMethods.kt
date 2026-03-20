package pay.payment.tests

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.restassured.RestAssured

import io.restassured.response.Response
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasItem
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import pay.xprojectdata.client.ApiClient
import pay.xprojectdata.config.EnvironmentConfig
import pay.xprojectdata.dto.request.NullFields
import pay.xprojectdata.dto.request.NullPayloadItem
import pay.xprojectdata.dto.request.SmNullFieldsRequestGenerator

import pay.xprojectdata.dto.request.SmRequestGenerator
import ru.testit.annotations.WorkItemIds
import java.util.stream.Stream

class POSTApiV1PaymentPayMethodUuidSavedMethods {
    private lateinit var apiClient: ApiClient
    private val mapper = jacksonObjectMapper()
    val basePath = "/api/v1/payment/pay/3a17ae5d-7de3-41a5-9f19-bf490c87b8a7"

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
            Arguments.of(mapOf<String, Any>(), "Пустой объект", "Поле account id обязательно для заполнения."),
            Arguments.of(listOf<Any>(), "Пустой массив", "Поле account id обязательно для заполнения."),
            Arguments.of(false, "Булево значение", "Поле account id должно быть строкой.")
        )

        @JvmStatic
        fun provideInvalidSecurePayValues(): Stream<Arguments> = Stream.of(
            Arguments.of(2, "Целое число", "Поле secure pay должно быть строкой."),
            Arguments.of(mapOf<String, Any>(), "Пустой объект", "Поле secure pay обязательно для заполнения."),
            Arguments.of(listOf<Any>(), "Пустой массив", "Поле secure pay обязательно для заполнения."),
            Arguments.of(false, "Булево значение", "Поле secure pay должно быть строкой.")
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
    @WorkItemIds("d5a334b2-4d9e-4a12-aaff-5c7d155a71c3")
    @DisplayName("200. Успешное выполнение запроса. platform = WEB, mobile = false")
    fun successPaymentSm() {
        val requestBody = SmRequestGenerator.baseRequest()
        val response: Response = apiClient.post(
            path = basePath,
            body = requestBody
        )

        response.then()
            .statusCode(200)
            .body("success", equalTo(true))
            .body("payment_details.system_id", notNullValue())
            .body("payment_details.entity_type", notNullValue())
            .body("payment_details.entity_info", notNullValue())
            .body("id", notNullValue())
            .body("transaction_id", notNullValue())
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("d5a334b2-4d9e-4a12-aaff-5c7d155a71c3")
    @DisplayName("200. Успешное выполнение запроса. platform = WEB, mobile = true")
    fun platformWebMobileTrue(){
        val modifiedRequest = SmRequestGenerator.baseRequest().copy(
            platform = "WEB",
            mobile = true
        )

        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(200)
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("d5a334b2-4d9e-4a12-aaff-5c7d155a71c3")
    @DisplayName("200. Успешное выполнение запроса. platform = IOS, mobile = true")
    fun platformIosMobileTrue(){
        val modifiedRequest = SmRequestGenerator.baseRequest().copy(
            platform = "IOS",
            mobile = true
        )

        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(200)
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("d5a334b2-4d9e-4a12-aaff-5c7d155a71c3")
    @DisplayName("200. Успешное выполнение запроса. platform = IOS, mobile = false")
    fun platformIosMobileFalse(){
        val modifiedRequest = SmRequestGenerator.baseRequest().copy(
            platform = "IOS",
            mobile = false
        )

        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(200)
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("d5a334b2-4d9e-4a12-aaff-5c7d155a71c3")
    @DisplayName("200. Успешное выполнение запроса. platform = ANDROID, mobile = true")
    fun platformAndroidMobileTrue(){
        val modifiedRequest = SmRequestGenerator.baseRequest().copy(
            platform = "ANDROID",
            mobile = true
        )

        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(200)
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("d5a334b2-4d9e-4a12-aaff-5c7d155a71c3")
    @DisplayName("200. Успешное выполнение запроса. platform = ANDROID, mobile = false")
    fun platformAndroidMobileFalse(){
        val modifiedRequest = SmRequestGenerator.baseRequest().copy(
            platform = "ANDROID",
            mobile = false
        )

        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(200)
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("d5a334b2-4d9e-4a12-aaff-5c7d155a71c3")
    @DisplayName("200. Успешное выполнение запроса. platform = WINDOWS_PHONE, mobile = true")
    fun platformWindowsPhoneMobileTrue(){
        val modifiedRequest = SmRequestGenerator.baseRequest().copy(
            platform = "WINDOWS_PHONE",
            mobile = true
        )

        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(200)
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("d5a334b2-4d9e-4a12-aaff-5c7d155a71c3")
    @DisplayName("200. Успешное выполнение запроса. platform = WINDOWS_PHONE, mobile = false")
    fun platformWindowsPhoneMobileFalse(){
        val modifiedRequest = SmRequestGenerator.baseRequest().copy(
            platform = "WINDOWS_PHONE",
            mobile = false
        )

        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(200)
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("a2e6914f-be31-445c-b21f-90e905c8324b")
    @DisplayName("200. Проверка nullable полей. phone = null")
    fun phoneNull(){
        val modifiedRequest = SmRequestGenerator.baseRequest().copy(phone = null)
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )
        response.then()
            .statusCode(200)
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("10cd5382-d1d4-496a-8af7-50e4f4eec69f")
    @DisplayName("400. Проверка обязательности полей. no account_id")
    fun noAccountId(){
        val noAmountRequestBody = SmRequestGenerator.noAccountIdRequest().copy()
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
                "errors.account_id", hasItem("Поле account id обязательно для заполнения.")
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("10cd5382-d1d4-496a-8af7-50e4f4eec69f")
    @DisplayName("400. Проверка обязательности полей. no secure_pay")
    fun noSecurePay(){
        val noAmountRequestBody = SmRequestGenerator.noSecurePayRequest().copy()
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
                "errors.secure_pay", hasItem("Поле secure pay обязательно для заполнения.")
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("10cd5382-d1d4-496a-8af7-50e4f4eec69f")
    @DisplayName("400. Проверка обязательности полей. no mobile")
    fun noMobile(){
        val noAmountRequestBody = SmRequestGenerator.noMobileRequest().copy()
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
                "errors.mobile", hasItem("Поле Моб. номер обязательно для заполнения.")
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("10cd5382-d1d4-496a-8af7-50e4f4eec69f")
    @DisplayName("400. Проверка обязательности полей. no platform")
    fun noPlatform(){
        val noAmountRequestBody = SmRequestGenerator.noPlatformRequest().copy()
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
                "errors.platform", hasItem("Поле platform обязательно для заполнения.")
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("10cd5382-d1d4-496a-8af7-50e4f4eec69f")
    @DisplayName("400. Проверка обязательности полей. no amount")
    fun noAmount(){
        val noAmountRequestBody = SmRequestGenerator.noAmountRequest().copy()
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
    @WorkItemIds("10cd5382-d1d4-496a-8af7-50e4f4eec69f")
    @DisplayName("400. Проверка обязательности полей. no invoice_id")
    fun noInvoiceId(){
        val noAmountRequestBody = SmRequestGenerator.noInvoiceIdRequest().copy()
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
                "errors.invoice_id", hasItem("Поле invoice id обязательно для заполнения.")
            )
    }

    @ParameterizedTest
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. Для поля amount: Строка, объект, пустой массив, булево")
    @MethodSource("provideInvalidAmountValues")
    fun amountInvalidValuesTest(amountValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(amount = amountValue)
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
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. amount = null")
    fun amountNull(){
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(amount = null)
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
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. Для поля invoice_id: Целое число, объект, пустой массив, булево")
    @MethodSource("provideInvalidInvoiceIdValues")
    fun invoiceIdInvalidValuesTest(invoiceIdValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(invoice_id = invoiceIdValue)
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
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. invoice_id = null")
    fun invoiceIdNull(){
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(invoice_id = null)
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
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. Для поля mobile: Целое число, строка, объект, пустой массив")
    @MethodSource("provideInvalidMobileValues")
    fun mobileInvalidValuesTest(mobileValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(mobile = mobileValue)
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
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. mobile = null")
    fun mobileNull(){
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(mobile = null)
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
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. Для поля platform: Целое число, объект, пустой массив, булево")
    @MethodSource("provideInvalidPlatformValues")
    fun platformInvalidValuesTest(platformValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(platform = platformValue)
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
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. platform = null")
    fun platformNull(){
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(platform = null)
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
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. Для поля account_id: Целое число, объект, пустой массив, булево")
    @MethodSource("provideInvalidAccountIdValues")
    fun accountIdInvalidValuesTest(accountIdValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(account_id = accountIdValue)
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
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. account_id = null")
    fun accountIdNull(){
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(account_id = null)
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
                "errors.account_id", hasItem("Поле account id обязательно для заполнения.")
            )
    }

    @ParameterizedTest
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. Для поля secure_pay: Целое число, объект, пустой массив, булево")
    @MethodSource("provideInvalidSecurePayValues")
    fun securePayInvalidValuesTest(securePayValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(secure_pay = securePayValue)
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
                "errors.secure_pay", hasItem(expectedErrorMessage)
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. secure_pay = null")
    fun securePayNull(){
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(secure_pay = null)
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
                "errors.secure_pay", hasItem("Поле secure pay обязательно для заполнения.")
            )
    }

    @ParameterizedTest
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. Для поля name: Целое число, объект, пустой массив, булево")
    @MethodSource("provideInvalidNameValues")
    fun nameInvalidValuesTest(nameValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(name = nameValue)
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
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. name = null")
    fun nameNull(){
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(name = null)
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
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. Для поля payload: Целое число, строка, булево")
    @MethodSource("provideInvalidPayloadValues")
    fun payloadInvalidValuesTest(payloadValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(payload = payloadValue)
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
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. payload = null")
    fun payloadNull(){
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(payload = null)
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
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. Для поля payload.key: Целое число, объект, пустой массив, булево")
    @MethodSource("provideInvalidPayloadKeyValues")
    fun payloadKeyInvalidValuesTest(payloadKeyValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(
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
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. payload.key = null")
    fun payloadToKeyNull(){
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(
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
        response.then()
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
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. Для поля payload.value: Целое число, объект, пустой массив, булево")
    @MethodSource("provideInvalidPayloadValueValues")
    fun payloadValueInvalidValuesTest(payloadValueValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(
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
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. payload.value = null")
    fun payloadToValueNull(){
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(
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
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. Для поля phone: Целое число, объект, пустой массив, булево")
    @MethodSource("provideInvalidPhoneValues")
    fun phoneInvalidValuesTest(phoneValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(
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

    @ParameterizedTest
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. Для поля email: Целое число, объект, пустой массив, булево")
    @MethodSource("provideInvalidEmailValues")
    fun emailInvalidValuesTest(emailValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(
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
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. email = null")
    fun emailNull(){
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(email = null)
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
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. Для поля description: Целое число, объект, пустой массив, булево")
    @MethodSource("provideInvalidDescriptionValues")
    fun descriptionInvalidValuesTest(descriptionValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(
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
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. description = null")
    fun descriptionNull(){
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(description = null)
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
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. Для поля fields: Целое число, строка, булево")
    @MethodSource("provideInvalidFieldsValues")
    fun fieldsInvalidValuesTest(fieldsValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(
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
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. fields = null")
    fun fieldsNull(){
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(fields = null)
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
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. Для поля fields.ttl: Число с плавающей точкой, строка, объект, пустой массив, булево")
    @MethodSource("provideInvalidFieldsTtlValues")
    fun fieldsTtlInvalidValuesTest(fieldsTtlValue: Any?, description: String, expectedErrorMessage: String) {
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(
            fields = NullFields(
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
    @WorkItemIds("32d8faba-2df8-46fc-9e0b-5a1808296787")
    @DisplayName("400. Валидации по типу данных. fields.ttl = null")
    fun fieldsToTtlNull(){
        val modifiedRequest = SmNullFieldsRequestGenerator.baseRequest().copy(
            fields = NullFields(
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
                "errors.'fields.ttl'", hasItem("Поле fields.ttl должно быть целым числом."),
                "errors.'fields.ttl'", hasItem("Поле fields.ttl должно быть не меньше 1.")
            )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("8fa0b2ab-f4df-4cd1-b2d6-58e4227d4885")
    @DisplayName("400. Другие валидации. invoice_id = 21 symbol")
    fun invoiceIdManySymbols(){
        val modifiedRequest = SmRequestGenerator.baseRequest().copy(
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
    @WorkItemIds("8fa0b2ab-f4df-4cd1-b2d6-58e4227d4885")
    @DisplayName("400. Другие валидации. email без @")
    fun emailWithoutDog(){
        val modifiedRequest = SmRequestGenerator.baseRequest().copy(
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
    @WorkItemIds("8fa0b2ab-f4df-4cd1-b2d6-58e4227d4885")
    @DisplayName("400. Другие валидации. email с пробелом")
    fun emailHaveAWhitespace(){
        val modifiedRequest = SmRequestGenerator.baseRequest().copy(
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
    @WorkItemIds("8fa0b2ab-f4df-4cd1-b2d6-58e4227d4885")
    @DisplayName("400. Другие валидации. email с двумя точками")
    fun emailWithDoublePoints(){
        val modifiedRequest = SmRequestGenerator.baseRequest().copy(
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
    @WorkItemIds("8fa0b2ab-f4df-4cd1-b2d6-58e4227d4885")
    @DisplayName("400. Другие валидации. phone с пробелом")
    fun phoneHaveAWhitespace(){
        val modifiedRequest = SmRequestGenerator.baseRequest().copy(
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
    @WorkItemIds("8fa0b2ab-f4df-4cd1-b2d6-58e4227d4885")
    @DisplayName("400. Другие валидации. phone начинается с 9")
    fun phoneStartAtNine(){
        val modifiedRequest = SmRequestGenerator.baseRequest().copy(
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
    @WorkItemIds("8fa0b2ab-f4df-4cd1-b2d6-58e4227d4885")
    @DisplayName("400. Другие валидации. phone начинается с 6")
    fun phoneStartAtSix(){
        val modifiedRequest = SmRequestGenerator.baseRequest().copy(
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
    @WorkItemIds("8fa0b2ab-f4df-4cd1-b2d6-58e4227d4885")
    @DisplayName("400. Другие валидации. phone начинается с 7 + 11 цифр после")
    fun phoneStartAtSevenPlusElevenNumbers(){
        val modifiedRequest = SmRequestGenerator.baseRequest().copy(
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

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("a606934a-74cc-46a6-ae64-b981104cf36f")
    @DisplayName("401. Ошибка авторизации. Отсутствует токен")
    fun noTokenSberGate() {
        val response = RestAssured
            .given()
            .post("/api/v1/payment/pay/3a17ae5d-7de3-41a5-9f19-bf490c87b8a7")

        response.then().statusCode(401)
            .body("error_message", equalTo("Ошибка авторизации или данная операция запрещена правами доступа"))
            .body("error_code", equalTo("S0.000002"))
            .body("type_error", equalTo("UNAUTHORIZED"))
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("a606934a-74cc-46a6-ae64-b981104cf36f")
    @DisplayName("401. Ошибка авторизации. Токен не валиден")
    fun noValidTokenSberGate() {
        val requestBody = SmRequestGenerator.baseRequest()
        val response = RestAssured
            .given()
            .body(requestBody)
            .header("Authorization", "Bearer e26ABDDy9HTV0gFoX1uCwdld9uSSjEYlrV7v0qrs2OfZOONm223XLMLKasdasd9GyPDMJFpmMIQLSPkG9XfCzT")
            .post("/api/v1/payment/pay/3a17ae5d-7de3-41a5-9f19-bf490c87b8a7")

        // Валидируем статус
        response.then().statusCode(401)
            .body("error_message", equalTo("Ошибка авторизации или данная операция запрещена правами доступа"))
            .body("error_code", equalTo("S0.000002"))
            .body("type_error", equalTo("UNAUTHORIZED"))
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("84f12780-fce2-4613-9b0a-05a53486edee")
    @DisplayName("423. Повторное проведение платежа с тем же invoice_id")
    fun repeatInvoiceIderror() {
        val modifiedRequest = SmRequestGenerator.baseRequest().copy()
        // Сохраняем invoice_id из первого запроса
        val firstInvoiceId = modifiedRequest.invoice_id
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
        )

        response.then()
            .statusCode(200)

        val modifiedRequestRepeat = SmRequestGenerator.baseRequest().copy(
            invoice_id = firstInvoiceId
        )
        val responseRepeat: Response = apiClient.post(
            path = basePath,
            body = modifiedRequestRepeat
        )

        responseRepeat.then()
            .statusCode(423)
            .body(
                "error_message", equalTo("Транзакция уже обработана"),
                "error_code", equalTo("S0.000013"),
                "type_error", equalTo("LOCKED")
            )
    }
}
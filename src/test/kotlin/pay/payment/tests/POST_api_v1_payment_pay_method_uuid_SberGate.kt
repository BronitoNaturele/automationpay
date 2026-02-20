//Классы тестовых сценариев. Вызывают методы ApiClient и проверяют ответы.

package pay.payment.tests

import io.restassured.response.Response
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.AfterEach

import pay.xprojectdata.client.ApiClient
import pay.xprojectdata.config.EnvironmentConfig
import pay.xprojectdata.dto.request.Fields
import pay.xprojectdata.dto.request.NullFields
import pay.xprojectdata.dto.request.NullPayloadItem
import pay.xprojectdata.dto.request.PayloadItem
import pay.xprojectdata.dto.request.SberGateNullFieldsRequestGenerator
import pay.xprojectdata.dto.request.SberGateRequestGenerator
import io.restassured.module.jsv.JsonSchemaValidator
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.DisplayName

class POST_api_v1_payment_pay_method_uuid_SberGate {

    private lateinit var apiClient: ApiClient
    val basePath = "/api/v1/payment/pay/d96d0e7f-771a-4c85-9f13-5eda4bca9251"

    @BeforeEach
    fun setUp() {
        val config = EnvironmentConfig.getConfigFromEnvVar()
        apiClient = ApiClient(config)
        //ApiLogger.enableLogging(logBody = true)
    }

    @AfterEach
    fun tearDown() {
        // ApiLogger.disableLogging()
    }

    @Test
    @DisplayName("202.Проверка валидации JSON схемы ответа")
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
    @DisplayName("202 - platform = IOS, mobile = true")
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
    @DisplayName("202 - platform = IOS, mobile = false")
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
    @DisplayName("202 - platform = ANDROID, mobile = true")
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
    @DisplayName("202 - platform = ANDROID, mobile = false")
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
    @DisplayName("202 - platform = WINDOWS_PHONE, mobile = true")
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
    @DisplayName("202 - platform = WINDOWS_PHONE, mobile = false")
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
    @DisplayName("202 - platform = WEB, mobile = true")
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
    @DisplayName("202 - platform = WEB, mobile = false")
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
    @DisplayName("202 - no account_id")
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
    @DisplayName("405 - method not allowed")
    fun methodNotAllowed(){
        val noAmountRequestBody = SberGateRequestGenerator.noAmountRequest().copy()
        val response: Response = apiClient.post(
            path = "/api/v1/payment/pay/",
            body = noAmountRequestBody
        )
        response.then()
            .statusCode(405)
            .assertThat()
            .body(
                "error_message", equalTo("The POST method is not supported for route api/v1/payment/pay. Supported methods: GET, HEAD."),
                "error_code", equalTo("405"),
                "type_error", equalTo("METHOD_NOT_ALLOWED"),
                "error_description", equalTo("")
            )
    }

    @Test
    @DisplayName("406 - no method_uuid")
    fun noMethodUuid(){
        val noAmountRequestBody = SberGateRequestGenerator.noAmountRequest().copy()
        val response: Response = apiClient.post(
            path = "/api/v1/payment/pay/:method_uuid",
            body = noAmountRequestBody
        )
        response.then()
            .statusCode(406)
            .assertThat()
            .body(
                "error_message", equalTo("Данный метод недоступен для платежа"),
                "error_code", equalTo("S0.000005"),
                "type_error", equalTo("NOT_ACCEPTABLE")
            )
    }

    @Test
    @DisplayName("400 - no amount")
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
    @DisplayName("400 - no mobile")
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
    @DisplayName("400 - no platform")
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
    @DisplayName("400 - no invoice_id")
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

    @Test
    @DisplayName("400 - amount = `1a!`")
    fun amountString(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(amount = "1a!")
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
                "errors.amount", hasItem("Поле amount должно быть числом.")
            )
    }

    @Test
    @DisplayName("400 - amount = `{}`")
    fun amountObject(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(amount = {})
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

    @Test
    @DisplayName("400 - amount = empty array")
    fun amountEmptyArray(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(amount = emptyList<Any?>())
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

    @Test
    @DisplayName("400 - amount = false")
    fun amountFalse(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(amount = false)
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
                "errors.amount", hasItem("Поле amount должно быть числом."),
                "errors.amount", hasItem("Поле amount должно быть не меньше 0.01.")
            )
    }

    @Test
    @DisplayName("400 - amount = null")
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

    @Test
    @DisplayName("400 - invoice_id = 1")
    fun invoiceIdInteger(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(invoice_id = 1)
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
                "errors.invoice_id", hasItem("Поле invoice id должно быть строкой.")
            )
    }

    @Test
    @DisplayName("400 - invoice_id = {}")
    fun invoiceIdObject(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(invoice_id = {})
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

    @Test
    @DisplayName("400 - invoice_id = empty array")
    fun invoiceIdEmptyArray(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(invoice_id = emptyList<Any?>())
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

    @Test
    @DisplayName("400 - invoice_id = false")
    fun invoiceIdFalse(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(invoice_id = false)
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
                "errors.invoice_id", hasItem("Поле invoice id должно быть строкой.")
            )
    }

    @Test
    @DisplayName("400 - invoice_id = null")
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

    @Test
    @DisplayName("400 - mobile = 2")
    fun mobileInteger(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(mobile = 2)
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
                "errors.mobile", hasItem("Поле Моб. номер должно иметь значение логического типа.")
            )
    }

    @Test
    @DisplayName("400 - mobile = `1a!`")
    fun mobileString(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(mobile = "1a!")
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
                "errors.mobile", hasItem("Поле Моб. номер должно иметь значение логического типа.")
            )
    }

    @Test
    @DisplayName("400 - mobile = {}")
    fun mobileObject(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(mobile = {})
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

    @Test
    @DisplayName("400 - mobile = empty array")
    fun mobileEmptyArray(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(mobile = emptyList<Any?>())
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

    @Test
    @DisplayName("400 - mobile = null")
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

    @Test
    @DisplayName("400 - platform = 1")
    fun platformInteger(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(platform = 1)
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
                "errors.platform", hasItem("Поле platform должно быть строкой."),
                "errors.platform", hasItem("Выбранное значение для platform ошибочно.")
            )
    }

    @Test
    @DisplayName("400 - platform = {}")
    fun platformObject(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(platform = {})
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

    @Test
    @DisplayName("400 - platform = empty array")
    fun platformEmptyArray(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(platform = emptyList<Any?>())
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

    @Test
    @DisplayName("400 - platform = false")
    fun platformFalse(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(platform = false)
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
                "errors.platform", hasItem("Поле platform должно быть строкой."),
                "errors.platform", hasItem("Выбранное значение для platform ошибочно.")
            )
    }

    @Test
    @DisplayName("400 - platform = null")
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

    @Test
    @DisplayName("400 - account_id = 1")
    fun accountIdInteger(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(account_id = 1)
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

    @Test
    @DisplayName("400 - account_id = {}")
    fun accountIdObject(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(account_id = {})
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

    @Test
    @DisplayName("400 - account_id = empty array")
    fun accountIdEmptyArray(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(account_id = emptyList<Any?>())
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

    @Test
    @DisplayName("400 - account_id = false")
    fun accountIdFalse(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(account_id = false)
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

    @Test
    @DisplayName("400 - account_id = null")
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

    @Test
    @DisplayName("400 - name = 1")
    fun nameInteger(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(name = 1)
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

    @Test
    @DisplayName("400 - name = {}")
    fun nameObject(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(name = {})
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

    @Test
    @DisplayName("400 - name = empty array")
    fun nameEmptyArray(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(name = emptyList<Any?>())
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

    @Test
    @DisplayName("400 - name = false")
    fun nameFalse(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(name = false)
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

    @Test
    @DisplayName("400 - name = null")
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

    @Test
    @DisplayName("400 - payload = 1")
    fun payloadInteger(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(payload = 1)
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

    @Test
    @DisplayName("400 - payload = `1a!`")
    fun payloadString(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(payload = "1a!")
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

    @Test
    @DisplayName("400 - payload = false")
    fun payloadFalse(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(payload = false)
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

    @Test
    @DisplayName("400 - payload = null")
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

    @Test
    @DisplayName("400 - payload to key = 1")
    fun payloadToKeyInteger(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            payload = PayloadItem(
                key = 1,
                value = "testValue"
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
                "errors.payload", hasItem("Поле payload должно быть массивом.")
            )
    }

    @Test
    @DisplayName("400 - payload to key = {}")
    fun payloadToKeyObject(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            payload = PayloadItem(
                    key = {},
                    value = "testValue"
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
                "errors.payload", hasItem("Поле payload должно быть массивом.")
            )
    }

    @Test
    @DisplayName("400 - payload to key = empty array")
    fun payloadToKeyEmptyArray(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            payload = PayloadItem(
                    key = emptyList<Any?>(),
                    value = "testValue"
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
                "errors.payload", hasItem("Поле payload должно быть массивом.")
            )
    }

    @Test
    @DisplayName("400 - payload to key = false")
    fun payloadToKeyFalse(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            payload = PayloadItem(
                    key = false,
                    value = "testValue"
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
                "errors.payload", hasItem("Поле payload должно быть массивом.")
            )
    }

    @Test
    @DisplayName("400 - payload to key = null")
    fun payloadToKeyNull(){
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(
            payload = NullPayloadItem(
                    key = null,
                    value = "testValue"
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
                "errors.payload", hasItem("Поле payload должно быть массивом.")
            )
    }

    @Test
    @DisplayName("400 - payload to value = 1")
    fun payloadToValueInteger(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            payload = PayloadItem(
                    key = "testKey",
                    value = 1
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
                "errors.payload", hasItem("Поле payload должно быть массивом.")
            )
    }

    @Test
    @DisplayName("400 - payload to value = {}")
    fun payloadToValueObject(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            payload = PayloadItem(
                    key = "testKey",
                    value = {}
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
                "errors.payload", hasItem("Поле payload должно быть массивом.")
            )
    }

    @Test
    @DisplayName("400 - payload to value = empty array")
    fun payloadToValueEmptyArray(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            payload = PayloadItem(
                    key = "testKey",
                    value = emptyList<Any?>()
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
                "errors.payload", hasItem("Поле payload должно быть массивом.")
            )
    }

    @Test
    @DisplayName("400 - payload to value = false")
    fun payloadToValueFalse(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            payload = PayloadItem(
                    key = "testKey",
                    value = false
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
                "errors.payload", hasItem("Поле payload должно быть массивом.")
            )
    }

    @Test
    @DisplayName("400 - payload to value = null")
    fun payloadToValueNull(){
        val modifiedRequest = SberGateNullFieldsRequestGenerator.baseRequest().copy(
            payload = NullPayloadItem(
                    key = "testKey",
                    value = null
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
                "errors.payload", hasItem("Поле payload должно быть массивом.")
            )
    }

    @Test
    @DisplayName("400 - phone = 1")
    fun phoneInteger(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(phone = 1)
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
                "errors.phone", hasItem("Поле Телефон должно быть строкой."),
                "errors.phone", hasItem("Поле Телефон имеет ошибочный формат.")
            )
    }

    @Test
    @DisplayName("400 - phone = {}")
    fun phoneObject(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(phone = {})
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
                "errors.phone", hasItem("Поле Телефон должно быть строкой."),
                "errors.phone", hasItem("Поле Телефон имеет ошибочный формат.")
            )
    }

    @Test
    @DisplayName("400 - phone = empty array")
    fun phoneEmptyArray(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(phone = emptyList<Any?>())
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
                "errors.phone", hasItem("Поле Телефон должно быть строкой."),
                "errors.phone", hasItem("Поле Телефон имеет ошибочный формат.")
            )
    }

    @Test
    @DisplayName("400 - phone = false")
    fun phoneFalse(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(phone = false)
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
                "errors.phone", hasItem("Поле Телефон должно быть строкой."),
                "errors.phone", hasItem("Поле Телефон имеет ошибочный формат.")
            )
    }

    @Test
    @DisplayName("400 - email = 1")
    fun emailInteger(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(email = 1)
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

    @Test
    @DisplayName("400 - email = {}")
    fun emailObject(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(email = {})
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

    @Test
    @DisplayName("400 - email = empty array")
    fun emailEmptyArray(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(email = emptyList<Any?>())
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

    @Test
    @DisplayName("400 - email = false")
    fun emailFalse(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(email = false)
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

    @Test
    @DisplayName("400 - email = null")
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

    @Test
    @DisplayName("400 - description = 1")
    fun descriptionInteger(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(description = 1)
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

    @Test
    @DisplayName("400 - description = {}")
    fun descriptionObject(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(description = {})
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

    @Test
    @DisplayName("400 - description = empty array")
    fun descriptionEmptyArray(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(description = emptyList<Any?>())
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

    @Test
    @DisplayName("400 - description = false")
    fun descriptionFalse(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(description = false)
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

    @Test
    @DisplayName("400 - description = null")
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

    @Test
    @DisplayName("400 - fields = 1")
    fun fieldsInteger(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(fields = 1)
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

    @Test
    @DisplayName("400 - fields = `1a!`")
    fun fieldsString(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(fields = "1a!")
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

    @Test
    @DisplayName("400 - fields = false")
    fun fieldsFalse(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(fields = false)
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

    @Test
    @DisplayName("400 - fields = null")
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

    @Test
    @DisplayName("400 - fields to successUrl = 1")
    fun fieldsToSuccessUrlInteger(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            fields = Fields(
                successUrl = 1,
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

    @Test
    @DisplayName("400 - fields to successUrl = {}")
    fun fieldsToSuccessUrlObject(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            fields = Fields(
                successUrl = {},
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

    @Test
    @DisplayName("400 - fields to successUrl = empty array")
    fun fieldsToSuccessUrlEmptyArray(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            fields = Fields(
                successUrl = emptyList<Any?>(),
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

    @Test
    @DisplayName("400 - fields to successUrl = false")
    fun fieldsToSuccessUrlFalse(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            fields = Fields(
                successUrl = false,
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

    @Test
    @DisplayName("400 - fields to successUrl = null")
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

    @Test
    @DisplayName("400 - fields to errorUrl = 1")
    fun fieldsToErrorUrlInteger(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            fields = Fields(
                successUrl = "https://uat-pay.av.ru/success/123456",
                errorUrl = 1,
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

    @Test
    @DisplayName("400 - fields to errorUrl = {}")
    fun fieldsToErrorUrlObject(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            fields = Fields(
                successUrl = "https://uat-pay.av.ru/success/123456",
                errorUrl = {},
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

    @Test
    @DisplayName("400 - fields to errorUrl = empty array")
    fun fieldsToErrorUrlEmptyArray(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            fields = Fields(
                successUrl = "https://uat-pay.av.ru/success/123456",
                errorUrl = emptyList<Any?>(),
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

    @Test
    @DisplayName("400 - fields to errorUrl = false")
    fun fieldsToErrorUrlFalse(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            fields = Fields(
                successUrl = "https://uat-pay.av.ru/success/123456",
                errorUrl = false,
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

    @Test
    @DisplayName("400 - fields to errorUrl = null")
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

    @Test
    @DisplayName("400 - fields to ttl = 1.1")
    fun fieldsToTtlFloat(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            fields = Fields(
                successUrl = "https://uat-pay.av.ru/success/123456",
                errorUrl = "https://uat-pay.av.ru//error/123456",
                ttl = 1.1
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
    @DisplayName("400 - fields to ttl = `1a!`")
    fun fieldsToTtlString(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            fields = Fields(
                successUrl = "https://uat-pay.av.ru/success/123456",
                errorUrl = "https://uat-pay.av.ru//error/123456",
                ttl = "1a!"
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
                "errors[\"fields.ttl\"]", hasItem("Поле fields.ttl должно быть целым числом.")
            )
    }

    @Test
    @DisplayName("400 - fields to ttl = {}")
    fun fieldsToTtlObject(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            fields = Fields(
                successUrl = "https://uat-pay.av.ru/success/123456",
                errorUrl = "https://uat-pay.av.ru//error/123456",
                ttl = {}
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
                "errors[\"fields.ttl\"]", hasItem("fields.ttl is greater than the maximum allowed.")
            )
    }

    @Test
    @DisplayName("400 - fields to ttl = empty array")
    fun fieldsToTtlEmptyArray(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            fields = Fields(
                successUrl = "https://uat-pay.av.ru/success/123456",
                errorUrl = "https://uat-pay.av.ru//error/123456",
                ttl = emptyList<Any?>()
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
                "errors[\"fields.ttl\"]", hasItem("fields.ttl is greater than the maximum allowed.")
            )
    }

    @Test
    @DisplayName("400 - fields to ttl = false")
    fun fieldsToTtlFalse(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(
            fields = Fields(
                successUrl = "https://uat-pay.av.ru/success/123456",
                errorUrl = "https://uat-pay.av.ru//error/123456",
                ttl = false
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
    @DisplayName("400 - fields to ttl = null")
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
    @DisplayName("400 - invoice_id = 21 symbol")
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
    @DisplayName("400 - email without @")
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
    @DisplayName("400 - email have a whitespace")
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
    @DisplayName("400 - email with double points")
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
    @DisplayName("400 - phone have a whitespace")
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
    @DisplayName("400 - phone start at 9")
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
    @DisplayName("400 - phone start at 6")
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
    @DisplayName("400 - phone start at 7 + 11 numbers")
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
//Классы тестовых сценариев. Вызывают методы ApiClient и проверяют ответы.

package tests

import io.restassured.response.Response
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.AfterEach

import client.ApiClient
import config.EnvironmentConfig
import dto.Request.Fields
import dto.Request.NullFields
import dto.Request.NullPayloadItem
import dto.Request.PayloadItem
import dto.Request.SberGateBodyRequest
import dto.Request.SberGateNullFieldsRequestGenerator
import dto.Request.SberGateRequestGenerator
import dto.Request.SberGateRequestGenerator.generateRandom10Digit
import io.restassured.module.jsv.JsonSchemaValidator
import logger.ApiLogger
import org.hamcrest.Matchers.*

class POST_api_v1_payment_pay_method_uuid_Sber_Gate {

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
    fun `202 - Validating the JSON scheme to the response with method_uuid`() {
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
                    "JsonSchema/POST_api_v1_payment_pay_method_uuid_Sber_Gate.json"
                )
            )
    }

    @Test
    fun `202 - platform = IOS, mobile = true`(){
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
                    "JsonSchema/POST_api_v1_payment_pay_method_uuid_Sber_Gate.json"
                )
            )
    }

    @Test
    fun `202 - platform = IOS, mobile = false`(){
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
                    "JsonSchema/POST_api_v1_payment_pay_method_uuid_Sber_Gate.json"
                )
            )
    }

    @Test
    fun `202 - platform = ANDROID, mobile = true`(){
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
                    "JsonSchema/POST_api_v1_payment_pay_method_uuid_Sber_Gate.json"
                )
            )
    }

    @Test
    fun `202 - platform = ANDROID, mobile = false`(){
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
                    "JsonSchema/POST_api_v1_payment_pay_method_uuid_Sber_Gate.json"
                )
            )
    }

    @Test
    fun `202 - platform = WINDOWS_PHONE, mobile = true`(){
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
                    "JsonSchema/POST_api_v1_payment_pay_method_uuid_Sber_Gate.json"
                )
            )
    }

    @Test
    fun `202 - platform = WINDOWS_PHONE, mobile = false`(){
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
                    "JsonSchema/POST_api_v1_payment_pay_method_uuid_Sber_Gate.json"
                )
            )
    }

    @Test
    fun `202 - platform = WEB, mobile = true`(){
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
                    "JsonSchema/POST_api_v1_payment_pay_method_uuid_Sber_Gate.json"
                )
            )
    }

    @Test
    fun `202 - platform = WEB, mobile = false`(){
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
                    "JsonSchema/POST_api_v1_payment_pay_method_uuid_Sber_Gate.json"
                )
            )
    }

    @Test
    fun `202 - no account_id`(){
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
                    "JsonSchema/POST_api_v1_payment_pay_method_uuid_Sber_Gate.json"
                )
            )
    }

    @Test
    fun `405 - method not allowed`(){
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
    fun `406 - no method_uuid`(){
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
    fun `400 - no amount`(){
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
    fun `400 - no mobile`(){
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
    fun `400 - no platform`(){
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
    fun `400 - no invoice_id`(){
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
    fun `400 - amount = "1a!"`(){
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
    fun `400 - amount = {}`(){
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
    fun `400 - amount = empty array`(){
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
    fun `400 - amount = false`(){
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
    fun `400 - amount = null`(){
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
    fun `400 - invoice_id = 1`(){
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
    fun `400 - invoice_id = {}`(){
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
    fun `400 - invoice_id = empty array`(){
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
    fun `400 - invoice_id = false`(){
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
    fun `400 - invoice_id = null`(){
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
    fun `400 - mobile = 2`(){
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
    fun `400 - mobile = "1a!"`(){
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
    fun `400 - mobile = {}`(){
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
    fun `400 - mobile = empty array`(){
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
    fun `400 - mobile = null`(){
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
    fun `400 - platform = 1`(){
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
    fun `400 - platform = {}`(){
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
    fun `400 - platform = empty array`(){
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
    fun `400 - platform = false`(){
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
    fun `400 - platform = null`(){
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
    fun `400 - account_id = 1`(){
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
    fun `400 - account_id = {}`(){
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
    fun `400 - account_id = empty array`(){
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
    fun `400 - account_id = false`(){
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
    fun `400 - account_id = null`(){
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
    fun `400 - name = 1`(){
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
    fun `400 - name = {}`(){
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
    fun `400 - name = empty array`(){
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
    fun `400 - name = false`(){
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
    fun `400 - name = null`(){
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
    fun `400 - payload = 1`(){
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
    fun `400 - payload = "1a!"`(){
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
    fun `400 - payload = false`(){
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
    fun `400 - payload = null`(){
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
    fun `400 - payload to key = 1`(){
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
    fun `400 - payload to key = {}`(){
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
    fun `400 - payload to key = empty array`(){
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
    fun `400 - payload to key = false`(){
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
    fun `400 - payload to key = null`(){
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
    fun `400 - payload to value = 1`(){
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
    fun `400 - payload to value = {}`(){
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
    fun `400 - payload to value = empty array`(){
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
    fun `400 - payload to value = false`(){
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
    fun `400 - payload to value = null`(){
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
    fun `400 - phone = 1`(){
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
    fun `400 - phone = {}`(){
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
    fun `400 - phone = empty array`(){
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
    fun `400 - to phone = false`(){
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
    fun `400 - email = 1`(){
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
    fun `400 - email = {}`(){
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
    fun `400 - email = empty array`(){
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
    fun `400 - email = false`(){
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
    fun `400 - email = null`(){
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
    fun `400 - description = 1`(){
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
    fun `400 - description = {}`(){
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
    fun `400 - description = empty array`(){
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
    fun `400 - description = false`(){
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
    fun `400 - description = null`(){
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
    fun `400 - fields = 1`(){
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
    fun `400 - fields = "1a!"`(){
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
    fun `400 - fields = false`(){
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
    fun `400 - fields = null`(){
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
    fun `400 - fields to successUrl = 1`(){
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
    fun `400 - fields to successUrl = {}`(){
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
    fun `400 - fields to successUrl = empty array`(){
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
    fun `400 - fields to successUrl = false`(){
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
    fun `400 - fields to successUrl = null`(){
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
    fun `400 - fields to errorUrl = 1`(){
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
    fun `400 - fields to errorUrl = {}`(){
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
    fun `400 - fields to errorUrl = empty array`(){
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
    fun `400 - fields to errorUrl = false`(){
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
    fun `400 - fields to errorUrl = null`(){
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
    fun `400 - fields to ttl = 1_1`(){
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
    fun `400 - fields to ttl = "1a!"`(){
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
    fun `400 - fields to ttl = {}`(){
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
    fun `400 - fields to ttl = empty array`(){
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
    fun `400 - fields to ttl = false`(){
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
    fun `400 - fields to ttl = null`(){
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
    fun `400 - invoice_id = 21 simbol`(){
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
    fun `400 - email without @`(){
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
    fun `400 - email have a whitespace`(){
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
    fun `400 - email with double points`(){
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
    fun `400 - phone have a whitespace`(){
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
    fun `400 - phone start at 9`(){
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
    fun `400 - phone start at 6`(){
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
    fun `400 - phone start at 7 + 11 numbers`(){
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
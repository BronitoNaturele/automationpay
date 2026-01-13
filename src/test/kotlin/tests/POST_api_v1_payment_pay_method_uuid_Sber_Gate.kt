//Классы тестовых сценариев. Вызывают методы ApiClient и проверяют ответы.

package tests

import io.restassured.response.Response
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.AfterEach

import client.ApiClient
import config.EnvironmentConfig
import dto.Request.Fields
import dto.Request.PayloadItem
import dto.Request.SberGateBodyRequest
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
    fun `Validating the JSON scheme to the response with method_uuid`() {
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
            .log().all() // Логируем запрос/ответ
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
            .log().all()
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
            .log().all()
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
            .log().all()
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
            .log().all()
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
            .log().all()
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
            .log().all()
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
    fun `400 - amount = null`(){
        val modifiedRequest = SberGateRequestGenerator.baseRequest().copy(amount = null)
        val response: Response = apiClient.post(
            path = basePath,
            body = modifiedRequest
            )
        response.then()
            .log().all()
            .statusCode(400)
            .assertThat()
            .body(
                "error_code", equalTo(400),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("BAD_REQUEST"),
                "errors.amount", hasItem("Поле amount обязательно для заполнения.")
            )
    }
}
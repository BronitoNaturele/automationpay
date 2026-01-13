//Классы тестовых сценариев. Вызывают методы ApiClient и проверяют ответы.

package tests

import io.restassured.response.Response
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.AfterEach

import client.ApiClient
import config.EnvironmentConfig
import dto.Request.SberGateRequestGenerator
import io.restassured.module.jsv.JsonSchemaValidator
import logger.ApiLogger
import io.restassured.RestAssured.*
import io.restassured.matcher.RestAssuredMatchers.*

class POST_api_v1_payment_pay_method_uuid_Sber_Gate {

    private lateinit var apiClient: ApiClient
    val basePath = "/api/v1/payment/pay/d96d0e7f-771a-4c85-9f13-5eda4bca9251"

    @BeforeEach
    fun setUp() {
        val config = EnvironmentConfig.getConfigFromEnvVar()
        apiClient = ApiClient(config)
        ApiLogger.enableLogging(logBody = true)
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
    fun `400 - no amount`(){
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
                "errors.amount", equalTo("Поле amount обязательно для заполнения.")
            )
    }
}
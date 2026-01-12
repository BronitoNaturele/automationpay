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
import utils.JsonUtils.JsonUtils


class POST_api_v1_payment_pay_method_uuid_Sber_Gate {
    private lateinit var apiClient: ApiClient

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
        // 1. Подготовка тела запроса
        val requestBody = SberGateRequestGenerator.baseRequest()

        val basePath = "/api/v1/payment/pay/d96d0e7f-771a-4c85-9f13-5eda4bca9251"

        // 3. Выполнение POST-запроса
        val response: Response = apiClient.post(
            path = basePath,
            body = requestBody,
            headers = emptyMap() // если нужны дополнительные заголовки — передайте их
        )
        println(response)

        // 4. Валидация ответа (единая цепочка)
        response.then()
            .log().all() // Логируем запрос/ответ
            .statusCode(202) // Проверяем статус-код
            .body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath(
                    "JsonSchema/POST_api_v1_payment_pay_method_uuid_Sber_Gate.json"
                )
            )

        // 5. Дополнительная проверка статуса (опционально)
        assertEquals(
            202,
            response.statusCode,
            "Ожидался код 202, но получен ${response.statusCode}: ${response.asString()}"
        )
    }
}
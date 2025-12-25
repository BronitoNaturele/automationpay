//Классы тестовых сценариев (Test Cases) UserApiTests, AuthApiTests и т. п. Вызывают методы ApiClient и проверяют ответы.

package tests

import io.restassured.response.Response
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.AfterEach

import logger.ApiLogger
import client.ApiClient
import config.EnvironmentConfig
import dto.Request.BodyPaymentMethodsResponse
import utils.JsonUtils.JsonUtils
import dto.Response.PaymentResponse

class PaymentMethodsTests {
    private lateinit var apiClient: ApiClient

    @BeforeEach
    fun setUp() {
        val config = EnvironmentConfig.getConfigFromEnvVar()
        apiClient = ApiClient(config)
        ApiLogger.enableLogging(logBody = true) // Включаем полное логирование
    }
    @AfterEach
    fun tearDown() {
        ApiLogger.disableLogging()
    }

    @Test
    fun `get payment methods returns valid response`() {
        val response: Response = apiClient.get("/api/v1/payment/methods")
            response.then()
            .log().all()
        assertEquals(200, response.statusCode) {
            "Ожидался код 200 OK, но получен ${response.statusCode}: ${response.asString()}"
        }
        val bodyPaymentMethodsResponse = BodyPaymentMethodsResponse(

        )
        val paymentResponse: PaymentResponse = JsonUtils.fromJson(
            response.asString(),
            PaymentResponse::class.java
        )
        assertNotNull(paymentResponse) { "Десериализация не удалась: paymentResponse == null" }
        assertNotNull(paymentResponse.data) { "Поле 'data' отсутствует в ответе" }
        assertNotNull(paymentResponse.name) { "Поле 'name' отсутствует в ответе" }
        assertNotNull(paymentResponse.uuid) { "Поле 'uuid' отсутствует в ответе" }
        assertNotNull(paymentResponse.type_id) { "Поле 'type_id' отсутствует в ответе" }
        assertNotNull(paymentResponse.weight) { "Поле 'weight' отсутствует в ответе" }


    }
}
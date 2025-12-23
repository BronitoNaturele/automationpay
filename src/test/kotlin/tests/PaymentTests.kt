//Классы тестовых сценариев (Test Cases)
//UserApiTests, AuthApiTests и т. п.
//Содержат тестовые методы для конкретных эндпоинтов.
//Используют JUnit5 (@Test, @ParameterizedTest и др.).
//Вызывают методы ApiClient и проверяют ответы.

package tests

import client.ApiClient
import config.EnvironmentConfig
import dto.Response.PaymentResponse
import validator.ResponseValidator.PaymentValidator
import validator.SchemaValidator.PaymentSchemaValidator
import io.restassured.response.Response
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.JsonUtils.JsonUtils
import org.junit.jupiter.api.Assertions.*

class PaymentTests {
    private lateinit var apiClient: ApiClient
    private val paymentValidator = PaymentValidator()
    private val schemaValidator = PaymentSchemaValidator()

    @BeforeEach
    fun setUp() {
        val config = EnvironmentConfig.getConfigFromEnvVar()
        apiClient = ApiClient(config)
    }

    @Test
    fun `get payment methods returns valid response`() {
        val response: Response = apiClient.get("/api/v1/payment/methods")

        paymentValidator.assertSuccess(response)
        paymentValidator.assertContentTypeJson(response)
        schemaValidator.validate(response)


        // Десериализация через JsonUtils
        val paymentResponse: PaymentResponse = JsonUtils.fromJson(
            response.asString(),
            PaymentResponse::class.java
        )

        assertEquals(4, paymentResponse.data.size, "Expected 4 payment methods")
        assertEquals("СБП", paymentResponse.data[0].name)
        assertEquals("Сохраненные способы", paymentResponse.data[1].name)
        assertEquals("Сбер", paymentResponse.data[2].name)
        assertEquals("Картой СГ", paymentResponse.data[3].name)
    }
}
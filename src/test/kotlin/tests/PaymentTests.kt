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
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*

class PaymentTests {
    private lateinit var apiClient: ApiClient
    private val paymentValidator = PaymentValidator()
    private val schemaValidator = PaymentSchemaValidator()
    private val objectMapper = ObjectMapper()

    @BeforeEach
    fun setUp() {
        val config = EnvironmentConfig.getConfigFromEnvVar()
        apiClient = ApiClient(config)
    }

    @Test
    fun `get payment methods returns valid response`() {
        // 1. Отправляем запрос
        val response: Response = apiClient.get("/api/v1/payment/methods")


        // 2. Валидируем HTTP-статус и Content-Type
        paymentValidator.assertSuccess(response)
        paymentValidator.assertContentTypeJson(response)


        // 3. Валидируем схему ответа
        schemaValidator.validate(response)

        // 4. Десериализуем в DTO и проверяем конкретные значения
        val paymentResponse: PaymentResponse = objectMapper.readValue(
            response.asString(),
            PaymentResponse::class.java
        )

        // Проверяем размер массива
        assertEquals(4, paymentResponse.data.size, "Expected 4 payment methods")

        // Проверяем имена
        assertEquals("СБП", paymentResponse.data[0].name)
        assertEquals("Сохраненные способы", paymentResponse.data[1].name)
        assertEquals("Сбер", paymentResponse.data[2].name)
        assertEquals("Картой СГ", paymentResponse.data[3].name)
    }
}
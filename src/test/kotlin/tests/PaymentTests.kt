//Классы тестовых сценариев (Test Cases)
//UserApiTests, AuthApiTests и т. п.
//Содержат тестовые методы для конкретных эндпоинтов.
//Используют JUnit5 (@Test, @ParameterizedTest и др.).
//Вызывают методы ApiClient и проверяют ответы.

package tests

import logger.ApiLogger

import client.ApiClient
import config.EnvironmentConfig
import dto.Request.PaymentRequest
import dto.Response.PaymentResponse
import validator.ResponseValidator.PaymentValidator
import validator.SchemaValidator.PaymentSchemaValidator
import io.restassured.response.Response
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.JsonUtils.JsonUtils
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.AfterEach


class PaymentTests {
    private lateinit var apiClient: ApiClient
    private val paymentValidator = PaymentValidator()
    private val schemaValidator = PaymentSchemaValidator()

    @BeforeEach
    fun setUp() {
        val config = EnvironmentConfig.getConfigFromEnvVar()
        apiClient = ApiClient(config)
        ApiLogger.enableFullLogging()  // Включаем логирование для всех тестов
    }
    @AfterEach
    fun tearDown() {
        ApiLogger.disableLogging()  // Очищаем фильтры после теста
    }

    @Test
    fun `get payment methods returns valid response`() {
        val response: Response = apiClient.get("/api/v1/payment/methods")



        // Проверка статуса и заголовков
        assertEquals(200, response.statusCode(), "Expected 200 OK")
        paymentValidator.assertContentTypeJson(response)

        // Валидация схемы и бизнес‑логики
        schemaValidator.validate(response)
        paymentValidator.assertSuccess(response)


        // Десериализация
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
    fun logger(){
        println(ApiLogger)
    }
}
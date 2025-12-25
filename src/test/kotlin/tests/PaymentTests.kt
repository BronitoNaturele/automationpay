//Классы тестовых сценариев (Test Cases) UserApiTests, AuthApiTests и т. п. Вызывают методы ApiClient и проверяют ответы.

package tests

import logger.ApiLogger
import client.ApiClient
import config.EnvironmentConfig
import dto.Response.PaymentMethod
import utils.JsonUtils.JsonUtils
import dto.Response.PaymentResponse
import validator.ResponseValidator.HttpStatusAssertions
import validator.SchemaValidator.PaymentSchemaValidator

import io.restassured.response.Response
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue


class PaymentTests {
    private lateinit var apiClient: ApiClient
    private val httpStatusAssertions = HttpStatusAssertions()
    private val schemaValidator = PaymentSchemaValidator<PaymentResponse, PaymentMethod> (
        PaymentResponse::class.java, // responseClass
        { it.name },               // nameSelector
        { it.uuid },               // uuidSelector
        { it.typeId },             // typeIdSelector
        { it.weight }              // weightSelector
    )

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
        httpStatusAssertions.assertSuccess(response)
        // Полная валидация схемы
        schemaValidator.validate(
            response,
            { it.data },
            { method ->
                schemaValidator.validateName(method)
                schemaValidator.validateUuid(method)
                schemaValidator.validateTypeId(method)
                schemaValidator.validateWeight(method)
            }
        )

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
}
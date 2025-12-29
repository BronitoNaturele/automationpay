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
import dto.Request.PaymentMethod
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

        //Десериализуем ответ в наш data class
        val paymentResponse: BodyPaymentMethodsResponse = JsonUtils.fromJson(
            response.asString(),
            BodyPaymentMethodsResponse::class.java
        )
        //Проверки структуры ответа
        assertNotNull(paymentResponse) { "Десериализация не удалась: paymentResponse == null" }

        //Проверяем наличие и непустоту массива data
        assertNotNull(paymentResponse.data) { "Поле 'data' отсутствует в ответе" }
        assertTrue(paymentResponse.data.isNotEmpty()) { "Список 'data' пуст" }


        //Проверяем каждый элемент в массиве data
        paymentResponse.data.forEachIndexed { index, method ->
            // Проверяем обязательные поля
            assertNotNull(method.name) { "Элемент #$index: поле 'name' отсутствует" }
            assertNotNull(method.uuid) { "Элемент #$index: поле 'uuid' отсутствует" }
            assertNotNull(method.type_id) { "Элемент #$index: поле 'type_id' отсутствует" }
            assertNotNull(method.weight) { "Элемент #$index: поле 'weight' отсутствует" }

            // Дополнительные проверки значений (пример)
            assertTrue(method.name.isNotEmpty()) { "Элемент #$index: поле 'name' не должно быть пустым" }
            assertTrue(method.weight >= 0) { "Элемент #$index: поле 'weight' должно быть >= 0" }
        }
    }
}
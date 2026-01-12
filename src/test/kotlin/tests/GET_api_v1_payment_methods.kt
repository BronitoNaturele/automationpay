//Классы тестовых сценариев. Вызывают методы ApiClient и проверяют ответы.

package tests

import io.restassured.response.Response
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.AfterEach

import client.ApiClient
import config.EnvironmentConfig
import dto.Request.BodyPaymentMethodsResponse
import io.restassured.module.jsv.JsonSchemaValidator
import utils.JsonUtils.JsonUtils

class GET_api_v1_payment_methods {
    private lateinit var apiClient: ApiClient

    @BeforeEach
    fun setUp() {
        val config = EnvironmentConfig.getConfigFromEnvVar()
        apiClient = ApiClient(config)
        //ApiLogger.enableLogging(logBody = true) // Включаем полное логирование
    }
    @AfterEach
    fun tearDown() {
        //ApiLogger.disableLogging()
    }

    @Test
    //Проверяем ответ на запрос, чтобы он соответствовал схеме JSON
    fun `Validating the JSON scheme to the response`() {
        val response: Response = apiClient.get("/api/v1/payment/methods")
        response.then()
            .log().all()
        response
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("JsonSchema/GET_api_v1_payment_methods.json"))

        assertEquals(200, response.statusCode) {
            "Ожидался код 200 OK, но получен ${response.statusCode}: ${response.asString()}"
        }
    }

    @Test
    //Проверяем наличие полей в ответе
    fun `Get payment methods returns valid response`() {
        val response: Response = apiClient.get("/api/v1/payment/methods")

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

    @Test
    //Проверяем наличие метода СБП
    fun `Checking for the method SBP in the response`() {
        val response: Response = apiClient.get("/api/v1/payment/methods")

        //Десериализуем ответ в наш data class
        val paymentResponse: BodyPaymentMethodsResponse = JsonUtils.fromJson(
            response.asString(),
            BodyPaymentMethodsResponse::class.java
        )

        //Проверяем каждый элемент в массиве data
        paymentResponse.data.forEachIndexed { index, method ->
            // Специфические проверки по name
            when (method.name) {

                "СБП" -> {
                    assertEquals("e9eafe9a-2c6a-449d-abbc-764f525a1f34", method.uuid)
                    assertEquals(7, method.type_id)
                    assertEquals(1004, method.weight)
                }
            }
        }
    }

    @Test
    //Проверяем наличие метода Сохраненные способы
    fun `Checking for the method Saved Methods in the response`() {
        val response: Response = apiClient.get("/api/v1/payment/methods")

        //Десериализуем ответ в наш data class
        val paymentResponse: BodyPaymentMethodsResponse = JsonUtils.fromJson(
            response.asString(),
            BodyPaymentMethodsResponse::class.java
        )

        //Проверяем каждый элемент в массиве data
        paymentResponse.data.forEachIndexed { index, method ->

            // Специфические проверки по name
            when (method.name) {

                "Сохраненные способы" -> {
                    assertEquals("3a17ae5d-7de3-41a5-9f19-bf490c87b8a7", method.uuid)
                    assertEquals(3, method.type_id)
                    assertEquals(100, method.weight)
                }
            }
        }
    }

    @Test
    //Проверяем наличие метода Сбер
    fun `Checking for the method Sber in the response`() {
        val response: Response = apiClient.get("/api/v1/payment/methods")

        //Десериализуем ответ в наш data class
        val paymentResponse: BodyPaymentMethodsResponse = JsonUtils.fromJson(
            response.asString(),
            BodyPaymentMethodsResponse::class.java
        )

        //Проверяем каждый элемент в массиве data
        paymentResponse.data.forEachIndexed { index, method ->

            // Специфические проверки по name
            when (method.name) {

                "Сбер" -> {
                    assertEquals("c961c5bd-0df7-46bc-9684-94baebc54a10", method.uuid)
                    assertEquals(4, method.type_id)
                    assertEquals(1, method.weight)
                }
            }
        }
    }

    @Test
    //Проверяем наличие метода Картой СГ
    fun `Checking for the method SberGate in the response`() {
        val response: Response = apiClient.get("/api/v1/payment/methods")

        //Десериализуем ответ в наш data class
        val paymentResponse: BodyPaymentMethodsResponse = JsonUtils.fromJson(
            response.asString(),
            BodyPaymentMethodsResponse::class.java
        )

        //Проверяем каждый элемент в массиве data
        paymentResponse.data.forEachIndexed { index, method ->

            // Специфические проверки по name
            when (method.name) {

                "Картой СГ" -> {
                    assertEquals("d96d0e7f-771a-4c85-9f13-5eda4bca9251", method.uuid)
                    assertEquals(6, method.type_id)
                    assertEquals(1, method.weight)
                }
            }
        }
    }
}
package pay.payment.tests

import configLogger
import io.restassured.response.Response
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.AfterEach

import pay.xprojectdata.client.ApiClient
import pay.xprojectdata.config.EnvironmentConfig
import io.restassured.module.jsv.JsonSchemaValidator
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName

class GET_api_v1_payment_methods {
    private lateinit var apiClient: ApiClient

    companion object {
        @BeforeAll
        @JvmStatic
        fun globalSetup() {
            configLogger.globalSetup()
        }
    }

    @BeforeEach
    fun setUp() {
        val config = EnvironmentConfig.getConfigFromEnvVar()
        apiClient = ApiClient(config)
    }

    @AfterEach
    fun tearDown() {

    }

    @Test
    @DisplayName("Проверка ответа на соответствие схеме JSON")
    // Проверяем ответ на запрос, чтобы он соответствовал схеме JSON
    fun checkTheResponseCodeAndJsonScheme() {
        val response: Response = apiClient.get("/api/v1/payment/methods")

        response
            .then()
            .statusCode(200)
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("pay.payment.jsonschema/GET_api_v1_payment_methods.json"))

    }

    @Test
    @DisplayName("Проверка наличия обязательных полей в ответе")
    // Проверяем наличие полей в ответе
    fun checkingForRequiredFieldsInTheResponse() {
        val response: Response = apiClient.get("/api/v1/payment/methods")

        response
            .then()
            .statusCode(200)
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("pay.payment.jsonschema/GET_api_v1_payment_methods.json"))

        // Извлекаем данные через JsonPath
        val jsonPath = response.jsonPath()

        // Проверки структуры ответа
        assertNotNull(jsonPath, "Ответ не содержит валидных JSON‑данных")

        // Проверяем наличие и непустоту массива data
        val data = jsonPath.getList<Any>("data")
        assertNotNull(data, "Поле 'data' отсутствует в ответе")
        assertTrue(data.isNotEmpty(), "Список 'data' пуст")

        // Проверяем каждый элемент в массиве data
        data.forEachIndexed { index, item ->
            // Приводим к Map<String, Any> без toString
            val map = when (item) {
                is Map<*, *> -> item as Map<String, Any>
                else -> throw IllegalArgumentException("Неподдерживаемый тип элемента")
            }

            // Проверяем обязательные поля
            assertNotNull(map["name"], "Элемент #$index: поле 'name' отсутствует")
            assertNotNull(map["uuid"], "Элемент #$index: поле 'uuid' отсутствует")
            assertNotNull(map["type_id"], "Элемент #$index: поле 'type_id' отсутствует")
            assertNotNull(map["weight"], "Элемент #$index: поле 'weight' отсутствует")

            // Дополнительные проверки
            assertTrue((map["name"] as? String)?.isNotEmpty() ?: false,
                "Элемент #$$index: поле 'name' не должно быть пустым")
            assertTrue((map["weight"] as? Int)?.let { it >= 0 } ?: false,
                "Элемент #$index: поле 'weight' должно быть >= 0")
        }
    }

    @Test
    @DisplayName("Проверка наличия метода СБП в ответе")
    // Проверяем наличие метода СБП
    fun checkingTheAvailabilityOfTheSbpPaymentMethodInTheResponse() {
        val response: Response = apiClient.get("/api/v1/payment/methods")
        val jsonPath = response.jsonPath()

        val sbpMethod = jsonPath
            .getList<Map<String, Any>>("data")
            .firstOrNull { it["name"] == "СБП" }

        requireNotNull(sbpMethod) { "Метод 'СБП' не найден в ответе" }

        assertEquals("e9eafe9a-2c6a-449d-abbc-764f525a1f34", sbpMethod["uuid"])
        assertEquals(7, sbpMethod["type_id"])
        assertEquals(1004, sbpMethod["weight"])
    }

    @Test
    @DisplayName("Проверка наличия метода Сохранённые способы в ответе")
    // Проверяем наличие метода Сохранённые способы
    fun checkingTheAvailabilityOfTheSavedMethodInTheResponse() {
        val response: Response = apiClient.get("/api/v1/payment/methods")
        val jsonPath = response.jsonPath()

        val savedMethod = jsonPath
            .getList<Map<String, Any>>("data")
            .firstOrNull { it["name"] == "Сохраненные способы" }

        requireNotNull(savedMethod) { "Метод 'Сохранённые способы' не найден в ответе" }

        assertEquals("3a17ae5d-7de3-41a5-9f19-bf490c87b8a7", savedMethod["uuid"])
        assertEquals(3, savedMethod["type_id"])
        assertEquals(100, savedMethod["weight"])
    }

    @Test
    @DisplayName("Проверка наличия метода Сбер в ответе")
    // Проверяем наличие метода Сбер
    fun checkingTheAvailabilityOfTheSberPaymentMethodInTheResponse() {
        val response: Response = apiClient.get("/api/v1/payment/methods")
        val jsonPath = response.jsonPath()

        val sberMethod = jsonPath
            .getList<Map<String, Any>>("data")
            .firstOrNull { it["name"] == "Сбер" }

        requireNotNull(sberMethod) { "Метод 'Сбер' не найден в ответе" }

        assertEquals("c961c5bd-0df7-46bc-9684-94baebc54a10", sberMethod["uuid"])
        assertEquals(4, sberMethod["type_id"])
        assertEquals(1, sberMethod["weight"])
    }

    @Test
    @DisplayName("Проверка наличия метода Картой СГ в ответе")
    // Проверяем наличие метода Картой СГ
    fun checkingTheAvailabilityOfTheSberGatePaymentMethodInTheResponse() {
        val response: Response = apiClient.get("/api/v1/payment/methods")
        val jsonPath = response.jsonPath()

        val sberGateMethod = jsonPath
            .getList<Map<String, Any>>("data")
            .firstOrNull { it["name"] == "Картой СГ" }

        requireNotNull(sberGateMethod) { "Метод 'Картой СГ' не найден в ответе" }

        assertEquals("d96d0e7f-771a-4c85-9f13-5eda4bca9251", sberGateMethod["uuid"])
        assertEquals(6, sberGateMethod["type_id"])
        assertEquals(1, sberGateMethod["weight"])
    }
}

package pay.payment.tests

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import configLogger
import io.restassured.RestAssured
import io.restassured.response.Response
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import pay.xprojectdata.client.ApiClient
import pay.xprojectdata.config.EnvironmentConfig
import io.restassured.module.jsv.JsonSchemaValidator
import pay.xprojectdata.dto.response.BodyPaymentMethodsResponse
import pay.xprojectdata.dto.response.PaymentMethod
import pay.xprojectdata.dto.response.noTokenBody
import ru.testit.annotations.WorkItemIds

class GETApiV1PaymentMethods {
    private lateinit var apiClient: ApiClient
    private val mapper = jacksonObjectMapper()

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
        // Очистка ресурсов при необходимости
        try {
            // Закрываем HTTP‑соединения
            io.restassured.RestAssured.reset()

            // Принудительное завершение потоков (если есть кастомные пулы)
            // ExecutorService?.shutdown()
        } catch (e: Exception) {
            println("Ошибка при очистке: ${e.message}")
        }
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("fa818c4e-b858-453c-9625-91e5767410e5")
    @DisplayName("Успешное выполнение запроса. 200. Проверка ответа на соответствие схеме JSON")
    fun checkTheResponseCodeAndJsonScheme() {
        val response: Response = apiClient.get("/api/v1/payment/methods")

        response
            .then()
            .statusCode(200)
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("pay.payment.jsonschema/GET_api_v1_payment_methods.json"))
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("fa818c4e-b858-453c-9625-91e5767410e5")
    @DisplayName("Проверка наличия обязательных полей в ответе")
    fun checkingForRequiredFieldsInTheResponse() {
        val response: Response = apiClient.get("/api/v1/payment/methods")

        response
            .then()
            .statusCode(200)
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("pay.payment.jsonschema/GET_api_v1_payment_methods.json"))

        // Парсим JSON напрямую в наш DTO
        val paymentResponse: BodyPaymentMethodsResponse = mapper.readValue(
            response.asString(),
            BodyPaymentMethodsResponse::class.java
        )

        // Проверяем наличие и непустоту данных
        assertNotNull(paymentResponse.data, "Поле 'data' отсутствует в ответе")
        assertTrue(paymentResponse.data.isNotEmpty(), "Список 'data' пуст")

        // Проверяем каждый элемент
        paymentResponse.data.forEachIndexed { index, paymentMethod ->
            validatePaymentMethod(paymentMethod, index)
        }
    }

    private fun validatePaymentMethod(method: PaymentMethod, index: Int) {
        // Обязательные поля (уже гарантированы структурой DTO)
        assertNotNull(method.name, "Элемент #$index: поле 'name' отсутствует")
        assertNotNull(method.uuid, "Элемент #$index: поле 'uuid' отсутствует")

        // Дополнительные проверки бизнес‑логики
        assertTrue(method.name.isNotEmpty(), "Элемент #$index: поле 'name' не должно быть пустым")
        assertTrue(method.weight >= 0, "Элемент #$index: поле 'weight' должно быть >= 0")
        assertTrue(
            method.type_id in 1..100,
            "Элемент #$index: type_id должен быть в диапазоне 1–100"
        )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("fa818c4e-b858-453c-9625-91e5767410e5")
    @DisplayName("Проверка наличия метода СБП в ответе")
    fun checkingTheAvailabilityOfTheSbpPaymentMethodInTheResponse() {
        val response: Response = apiClient.get("/api/v1/payment/methods")

        // Парсим ответ в DTO
        val paymentResponse: BodyPaymentMethodsResponse = mapper.readValue(
            response.asString(),
            BodyPaymentMethodsResponse::class.java
        )

        val sbpMethod = paymentResponse.data.firstOrNull { it.name == "СБП" }
        requireNotNull(sbpMethod) { "Метод 'СБП' не найден в ответе" }

        assertEquals("e9eafe9a-2c6a-449d-abbc-764f525a1f34", sbpMethod.uuid)
        assertEquals(7, sbpMethod.type_id)
        assertEquals(1004, sbpMethod.weight)
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("fa818c4e-b858-453c-9625-91e5767410e5")
    @DisplayName("Проверка наличия метода Сохранённые способы в ответе")
    fun checkingTheAvailabilityOfTheSavedMethodInTheResponse() {
        val response: Response = apiClient.get("/api/v1/payment/methods")

        // Парсим ответ в DTO
        val paymentResponse: BodyPaymentMethodsResponse = mapper.readValue(
            response.asString(),
            BodyPaymentMethodsResponse::class.java
        )

        val savedMethod = paymentResponse.data.firstOrNull { it.name == "Сохраненные способы" }
        requireNotNull(savedMethod) { "Метод 'Сохранённые способы' не найден в ответе" }

        assertEquals("3a17ae5d-7de3-41a5-9f19-bf490c87b8a7", savedMethod.uuid)
        assertEquals(3, savedMethod.type_id)
        assertEquals(100, savedMethod.weight)
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("fa818c4e-b858-453c-9625-91e5767410e5")
    @DisplayName("Проверка наличия метода Сбер в ответе")
    fun checkingTheAvailabilityOfTheSberPaymentMethodInTheResponse() {
        val response: Response = apiClient.get("/api/v1/payment/methods")

        // Парсим ответ в DTO
        val paymentResponse: BodyPaymentMethodsResponse = mapper.readValue(
            response.asString(),
            BodyPaymentMethodsResponse::class.java
        )

        val sberMethod = paymentResponse.data.firstOrNull { it.name == "Сбер" }
        requireNotNull(sberMethod) { "Метод 'Сбер' не найден в ответе" }

        assertEquals("c961c5bd-0df7-46bc-9684-94baebc54a10", sberMethod.uuid)
        assertEquals(4, sberMethod.type_id)
        assertEquals(1, sberMethod.weight)
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("fa818c4e-b858-453c-9625-91e5767410e5")
    @DisplayName("Проверка наличия метода Картой СГ в ответе")
    fun checkingTheAvailabilityOfTheSberGatePaymentMethodInTheResponse() {
        val response: Response = apiClient.get("/api/v1/payment/methods")

        // Парсим ответ в DTO
        val paymentResponse: BodyPaymentMethodsResponse = mapper.readValue(
            response.asString(),
            BodyPaymentMethodsResponse::class.java
        )

        val sberGateMethod = paymentResponse.data.firstOrNull { it.name == "Картой СГ" }
        requireNotNull(sberGateMethod) { "Метод 'Картой СГ' не найден в ответе" }

        assertEquals("d96d0e7f-771a-4c85-9f13-5eda4bca9251", sberGateMethod.uuid)
        assertEquals(6, sberGateMethod.type_id)
        assertEquals(1, sberGateMethod.weight)
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("23c9f834-f117-4b11-b237-96d6cddacf41")
    @DisplayName("Ошибка авторизации: 401. Нет токена")
    fun noTokenSberGatePaymentMethod() {
        // Выполняем запрос и сохраняем ответ
        val response = RestAssured
            .given()
            .get("/api/v1/payment/methods")

        // Валидируем статус
        response.then().statusCode(401)

        // Парсим в DTO
        val paymentResponse: noTokenBody = mapper.readValue(
            response.asString(),
            noTokenBody::class.java
        )

        // Проверяем поля с помощью JUnit assertions
        assertEquals(
            "Ошибка авторизации или данная операция запрещена правами доступа",
            paymentResponse.error_message,
            "Поле error_message должно содержать сообщение об ошибке авторизации"
        )
        assertEquals(
            "S0.000002",
            paymentResponse.error_code,
            "Поле error_code должно быть равно S0.000002"
        )
        assertEquals(
            "UNAUTHORIZED",
            paymentResponse.type_error,
            "Поле type_error должно быть равно UNAUTHORIZED"
        )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("23c9f834-f117-4b11-b237-96d6cddacf41")
    @DisplayName("Ошибка авторизации: 401. Токен не валиден")
    fun noValidTokenSberGatePaymentMethod() {
        // Выполняем запрос и сохраняем ответ
        val response = RestAssured
            .given()
            .header("Authorization", "Bearer e26ABDDy9HTV0gFoX1uCwdld9uSSjEYlrV7v0qrs2OfZOONm223XLMLKasdasd9GyPDMJFpmMIQLSPkG9XfCzT")
            .get("/api/v1/payment/methods")

        // Валидируем статус
        response.then().statusCode(401)

        // Парсим в DTO
        val paymentResponse: noTokenBody = mapper.readValue(
            response.asString(),
            noTokenBody::class.java
        )

        // Проверяем поля с помощью JUnit assertions
        assertEquals(
            "Ошибка авторизации или данная операция запрещена правами доступа",
            paymentResponse.error_message,
            "Поле error_message должно содержать сообщение об ошибке авторизации"
        )
        assertEquals(
            "S0.000002",
            paymentResponse.error_code,
            "Поле error_code должно быть равно S0.000002"
        )
        assertEquals(
            "UNAUTHORIZED",
            paymentResponse.type_error,
            "Поле type_error должно быть равно UNAUTHORIZED"
        )
    }
}
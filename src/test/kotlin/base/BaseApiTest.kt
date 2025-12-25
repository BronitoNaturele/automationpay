//Базовые классы для тестов (опционально). Содержит общую логику для всех тестовых классов (инициализация клиента, настройка заголовков и т. д.).
//Наследуется тестовыми сценариями.

package base

import client.ApiClient
import config.TestConfig
import io.restassured.response.Response
import org.junit.jupiter.api.BeforeEach
import utils.JsonUtils.JsonUtils
import org.junit.jupiter.api.Assertions.*

open class BaseApiTest {

    protected lateinit var apiClient: ApiClient
    protected lateinit var config: TestConfig

    @BeforeEach
    fun setUpBase() {
        // Загружаем конфигурацию (из properties/env-переменных)
        config = TestConfig.loadFromEnv()
        apiClient = ApiClient(config)
    }

    // --- Общие валидации HTTP‑ответов ---

    fun assertSuccess(response: Response) {
        assertEquals(200, response.statusCode) {
            "Expected HTTP 200 OK, but got ${response.statusCode}: ${response.asString()}"
        }
    }

    fun assertCreated(response: Response) {
        assertEquals(201, response.statusCode) {
            "Expected HTTP 201 Created, but got ${response.statusCode}"
        }
    }

    fun assertNotFound(response: Response) {
        assertEquals(404, response.statusCode) {
            "Expected HTTP 404 Not Found, but got ${response.statusCode}"
        }
    }

    fun assertBadRequest(response: Response) {
        assertEquals(400, response.statusCode) {
            "Expected HTTP 400 Bad Request, but got ${response.statusCode}"
        }
    }

    fun assertUnauthorized(response: Response) {
        assertEquals(401, response.statusCode) {
            "Expected HTTP 401 Unauthorized, but got ${response.statusCode}"
        }
    }

    // --- Утилиты для работы с телом ответа ---

    /**
     * Десериализует ответ в указанный тип T.
     * Использует JsonUtils (ваш общий утилитный класс).
     */
    fun <T : Any> parseResponse(response: Response, clazz: Class<T>): T {
        return JsonUtils.fromJson(response.asString(), clazz)
    }

    /**
     * Проверяет, что ответ содержит указанное поле в JSON.
     * Пример: checkFieldExists(response, "data.id")
     */
    fun checkFieldExists(response: Response, jsonPath: String) {
        response.then().body(jsonPath, org.hamcrest.Matchers.notNullValue())
    }

    /**
     * Проверяет значение поля в ответе.
     * Пример: checkFieldValue(response, "data.name", "Expected Name")
     */
    fun <T> checkFieldValue(response: Response, jsonPath: String, expectedValue: T) {
        response.then().body(jsonPath, org.hamcrest.Matchers.`is`(expectedValue))
    }

    // --- Вспомогательные методы для тестов ---

    /**
     * Отправляет GET‑запрос и сразу проверяет статус 200.
     */
    fun getAndExpectOk(path: String, headers: Map<String, String> = emptyMap()): Response {
        val response = apiClient.get(path, headers)
        assertSuccess(response)
        return response
    }

    /**
     * Отправляет POST‑запрос, десериализует ответ в тип T и проверяет статус 200/201.
     */
    fun <T : Any> postAndParse(
        path: String,
        body: Any,
        headers: Map<String, String> = emptyMap(),
        expectedStatus: Int = 200,
        clazz: Class<T>
    ): T {
        val response = apiClient.post(path, body, headers)

        when (expectedStatus) {
            200 -> assertSuccess(response)
            201 -> assertCreated(response)
            else -> assertEquals(expectedStatus, response.statusCode)
        }

        return parseResponse(response, clazz)
    }

    /**
     * Отправляет PUT‑запрос, десериализует ответ в тип T и проверяет статус 200.
     */
    fun <T : Any> putAndParse(
        path: String,
        body: Any,
        headers: Map<String, String> = emptyMap(),
        clazz: Class<T>
    ): T {
        val response = apiClient.put(path, body, headers)
        assertSuccess(response)
        return parseResponse(response, clazz)
    }

    /**
     * Отправляет DELETE‑запрос и проверяет статус 204 (No Content) или 200.
     */
    fun deleteAndExpectNoContent(path: String, headers: Map<String, String> = emptyMap()) {
        val response = apiClient.delete(path, headers)
        val status = response.statusCode
        assertTrue(status == 204 || status == 200) {
            "Expected HTTP 204 No Content or 200 OK, but got $status"
        }
    }
}
//Классы для отправки HTTP‑запросов и получения ответов
//ApiClient (основной клиент для взаимодействия с API)
//Содержит методы для GET, POST, PUT, DELETE и др.
//Настраивает базовый URL, заголовки, авторизацию.
//Использует OkHttp, Ktor или RestAssured (в зависимости от выбранного HTTP‑клиента).

import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.time.Duration

class ApiClient(
    private val baseUrl: String,
    private val timeoutSeconds: Long = 30
) {
    private val objectMapper: ObjectMapper = ObjectMapper()
        .registerKotlinModule()  // Важная строка для Kotlin!
        .findAndRegisterModules()

    private lateinit var request: RequestSpecification

    init {
        // Базовая конфигурация REST Assured
        RestAssured.baseURI = baseUrl
        RestAssured.timeout = Duration.ofSeconds(timeoutSeconds).toMillis()

        request = RestAssured.given()
            .contentType(ContentType.JSON)
            .log().all()  // Логирует все запросы (удобно для отладки)
    }

    /**
     * POST-запрос с JSON-телом
     * @param path — путь эндпоинта (например, "/users")
     * @param body — объект для сериализации в JSON
     * @param headers — дополнительные заголовки
     * @return — Response для дальнейших проверок
     */
    fun post(
        path: String,
        body: Any,
        headers: Map<String, String> = emptyMap()
    ): Response {
        val jsonBody = objectMapper.writeValueAsString(body)
        var spec = request

        headers.forEach { key, value ->
            spec = spec.header(key, value)
        }

        return spec
            .body(jsonBody)
            .post(path)
    }

    /**
     * GET-запрос
     */
    fun get(
        path: String,
        headers: Map<String, String> = emptyMap()
    ): Response {
        var spec = request
        headers.forEach { key, value ->
            spec = spec.header(key, value)
        }
        return spec.get(path)
    }

    /**
     * PUT-запрос
     */
    fun put(
        path: String,
        body: Any,
        headers: Map<String, String> = emptyMap()
    ): Response {
        val jsonBody = objectMapper.writeValueAsString(body)
        var spec = request
        headers.forEach { key, value ->
            spec = spec.header(key, value)
        }
        return spec.body(jsonBody).put(path)
    }

    /**
     * DELETE-запрос
     */
    fun delete(
        path: String,
        headers: Map<String, String> = emptyMap()
    ): Response {
        var spec = request
        headers.forEach { key, value ->
            spec = spec.header(key, value)
        }
        return spec.delete(path)
    }

    /**
     * Метод для десериализации ответа в нужный класс
     * @param response — полученный Response
     * @param clazz — класс для десериализации
     * @return — экземпляр класса T
     */
    inline fun <reified T> deserializeResponse(response: Response): T {
        return objectMapper.readValue(response.asString(), T::class.java)
    }
}
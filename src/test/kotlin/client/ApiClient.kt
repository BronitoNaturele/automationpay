//Классы для отправки HTTP‑запросов и получения ответов
//ApiClient (основной клиент для взаимодействия с API)
//Содержит методы для GET, POST, PUT, DELETE и др.
//Настраивает базовый URL, заголовки, авторизацию.
//Использует OkHttp, Ktor или RestAssured (в зависимости от выбранного HTTP‑клиента).
package client

import config.TestConfig
import io.restassured.RestAssured
import io.restassured.config.RestAssuredConfig
import io.restassured.http.ContentType
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification
import io.restassured.config.HttpClientConfig
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

class ApiClient(private val config: TestConfig) {
    @PublishedApi
    internal val objectMapper: ObjectMapper = ObjectMapper()
        .registerKotlinModule()
        .findAndRegisterModules()

    private lateinit var request: RequestSpecification

    init {
        RestAssured.baseURI = config.baseUrl

        // Настройка таймаутов через RestAssuredConfig
        val timeoutMillis = config.timeoutSeconds * 1000 // секунды → миллисекунды
        RestAssured.config = RestAssuredConfig.config()
            .httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", timeoutMillis)
                .setParam("http.socket.timeout", timeoutMillis)
            )

        request = RestAssured.given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer ${config.authToken}")
            .log().all()
    }

    fun post(
        path: String,
        body: Any,
        headers: Map<String, String> = emptyMap()
    ): Response {
        val jsonBody = objectMapper.writeValueAsString(body)
        var spec = request
        headers.forEach { key, value -> spec = spec.header(key, value) }
        return spec.body(jsonBody).post(path)
    }

    fun get(
        path: String,
        headers: Map<String, String> = emptyMap()
    ): Response {
        var spec = request
        headers.forEach { key, value -> spec = spec.header(key, value) }
        return spec.get(path)
    }

    fun put(
        path: String,
        body: Any,
        headers: Map<String, String> = emptyMap()
    ): Response {
        val jsonBody = objectMapper.writeValueAsString(body)
        var spec = request
        headers.forEach { key, value -> spec = spec.header(key, value) }
        return spec.body(jsonBody).put(path)
    }

    fun delete(
        path: String,
        headers: Map<String, String> = emptyMap()
    ): Response {
        var spec = request
        headers.forEach { key, value -> spec = spec.header(key, value) }
        return spec.delete(path)
    }

    inline fun <reified T> deserializeResponse(response: Response): T {
        return objectMapper.readValue(response.asString(), T::class.java)
    }
}
//Классы для отправки HTTP‑запросов и получения ответов ApiClient (основной клиент для взаимодействия с API). Содержит методы для GET, POST, PUT, DELETE и др.
//Настраивает базовый URL, заголовки, авторизацию.

package client

import config.TestConfig
import io.restassured.RestAssured //Точка входа для построения HTTP‑запросов. Содержит статические методы для настройки и отправки запросов.
import io.restassured.config.RestAssuredConfig //Класс для глобальной конфигурации Rest‑Assured. Позволяет задать: тайм‑ауты, настройки HTTP‑клиента, логирование, сериализаторы и др.
import io.restassured.http.ContentType //Используется для указания Content-Type и Accept в запросах.
import io.restassured.response.Response //Класс, представляющий ответ от сервера. Содержит: статус‑код (statusCode), заголовки (headers), тело ответа (body), cookies и др.
import io.restassured.specification.RequestSpecification //Интерфейс для настройки запроса до его отправки. Позволяет задать: базовые URI/пути, заголовки, параметры запроса, аутентификацию и др. Часто используется для повторного применения настроек
import io.restassured.config.HttpClientConfig //Класс для настройки HTTP‑клиента под Rest‑Assured (Apache HttpClient или OkHttp). Позволяет конфигурировать: пул соединений, SSL/TLS, прокси, таймауты на уровне клиента.

import utils.JsonUtils.JsonUtils
import com.fasterxml.jackson.core.type.TypeReference

class ApiClient(private val config: TestConfig) {

    private lateinit var request: RequestSpecification

    init {
        RestAssured.baseURI = config.baseUrl

        // Настройка таймаутов
        val timeoutMillis = config.timeoutSeconds * 1000
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
        body: Any?,
        headers: Map<String, String> = emptyMap()
    ): Response {
        if (body == null) {
            return request.apply {
                headers.forEach { key, value -> header(key, value) }
            }.post(path)
        }

        val jsonBody = try {
            JsonUtils.toJson(body)
        } catch (e: Exception) {
            throw IllegalArgumentException("Failed to serialize body: $body", e)
        }

        val spec = request.apply {
            headers.forEach { key, value -> header(key, value) }
        }

        return spec.body(jsonBody).post(path)
    }

    fun get(
        path: String,
        headers: Map<String, String> = emptyMap()
    ): Response {
        val spec = request.apply {
            headers.forEach { key, value -> header(key, value) }
        }
        return spec.get(path)
    }

    fun put(
        path: String,
        body: Any?,
        headers: Map<String, String> = emptyMap()
    ): Response {
        if (body == null) {
            return request.apply {
                headers.forEach { key, value -> header(key, value) }
            }.put(path)
        }

        val jsonBody = try {
            JsonUtils.toJson(body)
        } catch (e: Exception) {
            throw IllegalArgumentException("Failed to serialize body: $body", e)
        }

        val spec = request.apply {
            headers.forEach { key, value -> header(key, value) }
        }

        return spec.body(jsonBody).put(path)
    }

    fun delete(
        path: String,
        headers: Map<String, String> = emptyMap()
    ): Response {
        val spec = request.apply {
            headers.forEach { key, value -> header(key, value) }
        }
        return spec.delete(path)
    }

    /**
     * Десериализует тело ответа в указанный тип с использованием TypeReference.
     */
    inline fun <reified T> deserializeResponse(response: Response): T {
        return JsonUtils.fromJsonWithTypeReference(
            response.asString(),
            object : TypeReference<T>() {}
        )
    }
}
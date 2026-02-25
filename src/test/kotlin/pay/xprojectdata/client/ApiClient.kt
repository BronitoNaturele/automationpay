package pay.xprojectdata.client

import pay.xprojectdata.config.TestConfig
import io.restassured.RestAssured
import io.restassured.config.RestAssuredConfig
import io.restassured.http.ContentType
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification
import io.restassured.config.HttpClientConfig

class ApiClient(private val config: TestConfig) {

    private var request: RequestSpecification

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
            .header("Accept", "application/json")
    }

    fun post(
        path: String,
        body: Any?,
        headers: Map<String, String> = emptyMap()
    ): Response {
        val spec = request.apply {
            headers.forEach { key, value -> header(key, value) }
        }

        return if (body == null) {
            spec.post(path)
        } else {
            spec.body(body).post(path)
        }
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
        val spec = request.apply {
            headers.forEach { key, value -> header(key, value) }
        }

        return if (body == null) {
            spec.put(path)
        } else {
            spec.body(body).put(path)
        }
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
}
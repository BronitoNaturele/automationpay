//Классы для отправки HTTP‑запросов и получения ответов
//ApiClient (основной клиент для взаимодействия с API)
//Содержит методы для GET, POST, PUT, DELETE и др.
//Настраивает базовый URL, заголовки, авторизацию.
//Использует OkHttp, Ktor или RestAssured (в зависимости от выбранного HTTP‑клиента).
package client

import config.TestConfig
import io.restassured.RestAssured //Точка входа для построения HTTP‑запросов. Содержит статические методы для настройки и отправки запросов.
import io.restassured.config.RestAssuredConfig //Класс для глобальной конфигурации Rest‑Assured. Позволяет задать: тайм‑ауты, настройки HTTP‑клиента, логирование, сериализаторы и др.
import io.restassured.http.ContentType //Используется для указания Content-Type и Accept в запросах.
import io.restassured.response.Response //Класс, представляющий ответ от сервера. Содержит: статус‑код (statusCode), заголовки (headers), тело ответа (body), cookies и др.
import io.restassured.specification.RequestSpecification //Интерфейс для настройки запроса до его отправки. Позволяет задать: базовые URI/пути, заголовки, параметры запроса, аутентификацию и др. Часто используется для повторного применения настроек
import io.restassured.config.HttpClientConfig //Класс для настройки HTTP‑клиента под Rest‑Assured (Apache HttpClient или OkHttp). Позволяет конфигурировать: пул соединений, SSL/TLS, прокси, таймауты на уровне клиента.
import com.fasterxml.jackson.databind.ObjectMapper //Главный класс библиотеки Jackson. Отвечает за: сериализацию (Java/Kotlin‑объект → JSON), десериализацию (JSON → Java/Kotlin‑объект).
import com.fasterxml.jackson.module.kotlin.registerKotlinModule //Расширение для Jackson, добавляющее поддержку Kotlin. Позволяет корректно работать с: data‑классами, nullability (?), свойствами с геттерами/сеттерами, перечислениями (enum).

class ApiClient(private val config: TestConfig) {
    //аннотация (для компилятора). Говорит: «Это API, которое можно использовать внутри модуля, но не снаружи».
    @PublishedApi
    //internal — модификатор видимости: поле доступно только в этом модуле (проекте), но не из других библиотек/модулей.
    //val objectMapper: ObjectMapper — объявляем неизменяемое поле типа ObjectMapper (это главный класс библиотеки Jackson для работы с JSON).
    //= ObjectMapper() — инициализируем: создаём новый экземпляр ObjectMapper.
    internal val objectMapper: ObjectMapper = ObjectMapper()
    //Это цепочка вызовов методов для настройки ObjectMapper:
    //.registerKotlinModule() — подключает поддержку Kotlin: правильно обрабатывает data class; учитывает nullable‑типы (String?); работает с Kotlin‑перечислениями (enum class).
    //.findAndRegisterModules() — ищет и подключает дополнительные модули Jackson (например, для работы с Java 8 датами, XML и т. п.). Это полезно, если в проекте используются сложные типы данных.
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
        println("=== Текущий URL ===")
        println(RestAssured.baseURI)  // выведите значение, которое реально используется
        println("Длина URL: ${RestAssured.baseURI.length}")
        println("Символ на позиции 22: '${RestAssured.baseURI[22]}'")
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
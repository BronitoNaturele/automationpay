//Логирование запросов/ответов (URL, заголовки, тело, статус).
//Настройка уровней логирования (DEBUG, INFO).
//Интеграция с SLF4J + Logback.
package logger

import io.restassured.filter.Filter
import io.restassured.filter.FilterChain
import io.restassured.specification.FilterableRequestSpecification
import io.restassured.specification.FilterableResponseSpecification
import io.restassured.response.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ApiLogger : Filter {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
    private var logHeaders = true
    private var logRequestBody = true
    private var logResponseBody = true

    fun withHeaders(enabled: Boolean): ApiLogger {
        logHeaders = enabled
        return this
    }

    fun withRequestBody(enabled: Boolean): ApiLogger {
        logRequestBody = enabled
        return this
    }

    fun withResponseBody(enabled: Boolean): ApiLogger {
        logResponseBody = enabled
        return this
    }

    override fun filter(
        requestSpec: FilterableRequestSpecification,
        responseSpec: FilterableResponseSpecification,
        ctx: FilterChain
    ): Response {
        logRequest(requestSpec)
        val response = ctx.next(requestSpec, responseSpec)
        logResponse(response)
        return response
    }

    private fun logRequest(requestSpec: FilterableRequestSpecification) {
        val timestamp = LocalDateTime.now().format(formatter)
        val method = requestSpec.method ?: "UNKNOWN"
        val uri = requestSpec.getURI()

        println("[API-LOG] [$timestamp] $method $uri")

        if (logHeaders) {
            requestSpec.headers().asList().forEach { header ->
                println("  → HEADER: ${header.getKey()}: ${header.getValue()}")
            }
        }

        if (logRequestBody) {
            val bodyObj = requestSpec.body
            if (bodyObj != null) {
                val bodyStr = when (bodyObj) {
                    is String -> bodyObj
                    is ByteArray -> bodyObj.decodeToString()
                    else -> bodyObj.toString()
                }
                val safeBody = bodyStr.replace(Regex("Bearer \\S+"), "Bearer [REDACTED]")
                println("  → BODY: $safeBody")
            }
        }
    }

    private fun logResponse(response: Response) {
        val statusCode = response.statusCode()
        val statusLine = response.statusLine()

        println("← [RESPONSE] $statusLine")

        if (logResponseBody) {
            val body = response.body().asString()
            if (body.isNotEmpty()) {
                val safeBody = body
                    .replace(Regex("\"token\"\\s*:\\s*\"[^\"]+\""), "\"token\": \"[REDACTED]\"")
                    .replace(Regex("\"password\"\\s*:\\s*\"[^\"]+\""), "\"password\": \"[REDACTED]\"")
                println("  ← BODY: $safeBody")
            } else {
                println("  ← BODY: (empty)")
            }
        }

        val timeMs = response.time()
        println("  ← TIME: $timeMs ms")
    }
}
package validator.ResponseValidator

import io.restassured.response.Response
import com.fasterxml.jackson.core.JsonProcessingException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper


class PaymentValidator {

    private val objectMapper = ObjectMapper()

    fun assertSuccess(response: Response) {
        val body = response.asString()

        // Проверка на пустой или null-ответ
        if (body.isNullOrBlank()) {
            throw AssertionError("Response body is empty or null")
        }

        try {
            // Попытка десериализовать JSON — проверяет синтаксическую валидность
            val jsonNode: JsonNode = objectMapper.readTree(body)

            // Убедимся, что JSON — это объект (начинается с {), а не примитив/массив
            if (!jsonNode.isObject()) {
                throw AssertionError(
                    "Response JSON should be an object (starting with '{'). " +
                            "Got: ${jsonNode.nodeType} (value: $jsonNode)"
                )
            }

            // Дополнительно: можно проверить, что объект не пустой
            if (jsonNode.size() == 0) {
                throw AssertionError("Response JSON object is empty")
            }

        } catch (e: JsonProcessingException) {
            throw AssertionError("Invalid JSON format in response: $body", e)
        } catch (e: Exception) {
            throw AssertionError("Failed to process response body: $body", e)
        }
    }

    fun assertContentTypeJson(response: Response) {
        val contentType = response.contentType()
        assertTrue(contentType.startsWith("application/json"),
            "Content-Type should start with 'application/json' (got: $contentType)")
    }
}
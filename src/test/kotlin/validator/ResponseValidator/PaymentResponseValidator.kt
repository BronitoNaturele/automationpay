package validator.ResponseValidator

import io.restassured.response.Response
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper


class PaymentValidator {

    private val objectMapper = ObjectMapper()

    fun assertSuccess(response: Response) {
        val body = response.asString()

        // Проверка на пустой ответ
        assertTrue(body.isNotEmpty(), "Response body is empty")

        try {
            // Десериализация JSON для строгой проверки
            val jsonNode: JsonNode = objectMapper.readTree(body)

            // Проверка полей success или status
            if (jsonNode.has("success")) {
                assertTrue(jsonNode.get("success").asBoolean(),
                    "Field 'success' should be true")
            } else if (jsonNode.has("status")) {
                assertEquals("success", jsonNode.get("status").asText(),
                    "Field 'status' should be 'success'")
            } else {
                throw AssertionError("Response should contain 'success' or 'status' field")
            }
        } catch (e: Exception) {
            throw AssertionError("Failed to parse JSON response: $body", e)
        }
    }

    fun assertContentTypeJson(response: Response) {
        val contentType = response.contentType()
        assertTrue(contentType.startsWith("application/json"),
            "Content-Type should start with 'application/json' (got: $contentType)")
    }
}
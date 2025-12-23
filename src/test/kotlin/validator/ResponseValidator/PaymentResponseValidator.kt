package validator.ResponseValidator

import io.restassured.response.Response
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

class PaymentValidator {

    fun assertSuccess(response: Response) {
        assertEquals(200, response.statusCode, "HTTP status code should be 200 OK")
    }

    fun assertContentTypeJson(response: Response) {
        val contentType = response.contentType
        assertTrue(
            contentType.contains("application/json"),
            "Content-Type should include 'application/json', but was: $contentType"
        )
    }
}
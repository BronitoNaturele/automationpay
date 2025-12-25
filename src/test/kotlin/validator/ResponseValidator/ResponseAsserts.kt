//Набор asserts для ответов на запросы

package validator.ResponseValidator

import io.restassured.response.Response
import org.junit.jupiter.api.Assertions.*


open class HttpStatusAssertions {

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

    fun assertUnprocessableContent(response: Response) {
        assertEquals(422, response.statusCode) {
            "Expected HTTP 422 UNPROCESSABLE_CONTENT, but got ${response.statusCode}"
        }
    }
}
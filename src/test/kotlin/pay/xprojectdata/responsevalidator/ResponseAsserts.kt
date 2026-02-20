//Набор asserts для ответов на запросы

package pay.xprojectdata.responsevalidator

import pay.xprojectdata.dto.response.BodyPaymentMethodsResponse

import io.restassured.response.Response
import org.junit.jupiter.api.Assertions.*
import pay.xprojectdata.utils.jsonutils.JsonUtils
import kotlin.test.DefaultAsserter.fail


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

open class PaymentMethodsFieldsAsserts(private val response: Response){
    private val paymentResponse: BodyPaymentMethodsResponse by lazy {
        JsonUtils.fromJson(response.asString(), BodyPaymentMethodsResponse::class.java)
    }

    fun CheckSbpMethod() {
        val found = paymentResponse.data.firstOrNull { it.name == "СБП" }

        if (found == null) {
            fail("Элемент с name='СБП' не найден. Доступные имена: ${paymentResponse.data.map { it.name }}")
        }
        assertEquals("СБП", found.name)
    }

    fun CheckSavedMethods(){
        val found = paymentResponse.data.firstOrNull { it.name == "Сохраненные способы" }

        if (found == null) {
            fail("Элемент с name='Сохраненные способы' не найден. Доступные имена: ${paymentResponse.data.map { it.name }}")
        }
        assertEquals("Сохраненные способы", found.name)
    }

    fun CheckSberMethod(){
        val found = paymentResponse.data.firstOrNull { it.name == "Сбер" }

        if (found == null) {
            fail("Элемент с name='Сбер' не найден. Доступные имена: ${paymentResponse.data.map { it.name }}")
        }
        assertEquals("Сбер", found.name)
    }

    fun CheckSberGateMethod(){
        val found = paymentResponse.data.firstOrNull { it.name == "Картой СГ" }

        if (found == null) {
            fail("Элемент с name='Картой СГ' не найден. Доступные имена: ${paymentResponse.data.map { it.name }}")
        }
        assertEquals("Картой СГ", found.name)
    }
}
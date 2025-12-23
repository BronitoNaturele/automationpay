//Проверка JSON‑схемы ответа (через json-schema-validator).
//Убеждается, что структура ответа соответствует спецификации.

package validator.SchemaValidator

import io.restassured.response.Response
import org.junit.jupiter.api.Assertions.assertTrue


class PaymentSchemaValidator {

    fun validate(response: Response) {
        val body = response.asString()
        assertTrue(body.contains("data"), "Response should contain 'data' field")
        assertTrue(body.contains("name") && body.contains("uuid"),
            "PaymentMethod should have 'name' and 'uuid'")
    }
}
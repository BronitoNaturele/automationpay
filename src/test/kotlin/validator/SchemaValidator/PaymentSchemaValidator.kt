package validator.SchemaValidator

import dto.Response.PaymentResponse
import io.restassured.response.Response
import utils.JsonUtils.JsonUtils  // ← правильный импорт!
import org.junit.jupiter.api.Assertions.*
import java.util.regex.Pattern

class PaymentSchemaValidator {
    private val uuidPattern = Pattern.compile(
        ("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")
    )

    fun validate(response: Response) {
        val parsed = parseResponse(response)

        assertTrue(parsed.data.isNotEmpty(), "Response 'data' array should not be empty")

        for (method in parsed.data) {
            // name
            assertNotNull(method.name, "Payment method 'name' should not be null")
            assertTrue(method.name.isNotBlank(), "Payment method 'name' should not be blank")

            // uuid
            assertNotNull(method.uuid, "Payment method 'uuid' should not be null")
            assertTrue(
                uuidPattern.matcher(method.uuid).matches(),
                "Payment method 'uuid' is not a valid UUID: ${method.uuid}"
            )

            // type_id
            assertTrue(
                method.typeId >= 0,
                "Payment method 'type_id' should be >= 0, but was: ${method.typeId}"
            )

            // weight
            assertTrue(
                method.weight >= 0,
                "Payment method 'weight' should be >= 0, but was: ${method.weight}"
            )
        }
    }

    private fun parseResponse(response: Response): PaymentResponse {
        return JsonUtils.fromJson(response.asString(), PaymentResponse::class.java)
    }
}
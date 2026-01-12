//Проверка JSON‑схемы ответа с дженерик классом и селекторами.
//Убеждается, что структура ответа соответствует спецификации.

package validator.SchemaValidator

import io.restassured.response.Response
import utils.JsonUtils.JsonUtils  // ← правильный импорт!
import org.junit.jupiter.api.Assertions.*

import java.util.regex.Pattern

class GET_api_v1_payment_methods_SchemaValidator<T : Any, E> constructor(
    private val responseClass: Class<T>,
    private val nameSelector: (E) -> String?,
    private val uuidSelector: (E) -> String?,
    private val typeIdSelector: (E) -> Int,
    private val weightSelector: (E) -> Int
) {
    private val uuidPattern = Pattern.compile(
        ("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")
    )

    fun validate(
        response: Response,
        dataSelector: (T) -> List<E>,
        itemValidator: (E) -> Unit
    ) {
        val parsed = parseResponse(response)
        validateDataNotEmpty(parsed, dataSelector)
        dataSelector(parsed).forEach { itemValidator(it) }
    }

    fun validateDataNotEmpty(parsed: T, dataSelector: (T) -> List<E>) {
        val data = dataSelector(parsed)
        assertTrue(data.isNotEmpty(), "Response 'data' array should not be empty")
    }

    fun validateName(method: E) {
        val name = nameSelector(method)
        assertNotNull(name, "Payment method 'name' should not be null")

        name?.let {
            assertTrue(it.isNotBlank(), "Payment method 'name' should not be blank")
        } ?: throw AssertionError("Payment method 'name' should not be null") // на всякий случай
    }

    fun validateUuid(method: E) {
        val uuid = uuidSelector(method)
        assertNotNull(uuid, "Payment method 'uuid' should not be null")
        assertTrue(
            uuidPattern.matcher(uuid).matches(),
            "Payment method 'uuid' is not a valid UUID: $uuid"
        )
    }

    fun validateTypeId(method: E) {
        val typeId = typeIdSelector(method)
        assertTrue(
            typeId >= 0,
            "Payment method 'type_id' should be >= 0, but was: $typeId"
        )
    }

    fun validateWeight(method: E) {
        val weight = weightSelector(method)
        assertTrue(
            weight >= 0,
            "Payment method 'weight' should be >= 0, but was: $weight"
        )
    }

    private fun parseResponse(response: Response): T {
        return JsonUtils.fromJson(response.asString(), responseClass)
    }
}
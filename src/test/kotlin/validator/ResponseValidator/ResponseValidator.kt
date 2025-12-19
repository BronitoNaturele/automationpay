//Классы проверок (Assertions/Validators)
//validator.ResponseValidator.ResponseValidator
//Централизует проверки статусов, схем, полей.
//Позволяет повторно использовать логику валидации.

package validator.ResponseValidator

class ResponseValidator {
    fun assertStatusCode(response: Response, expected: Int) {
        assertEquals(expected, response.statusCode)
    }

    fun assertFieldEquals(response: Response, path: String, expected: Any) {
        assertEquals(expected, response.jsonPath(path))
    }
}
//Классы проверок (Assertions/Validators)
//ResponseValidator
//Централизует проверки статусов, схем, полей.
//Позволяет повторно использовать логику валидации.

class ResponseValidator {
    fun assertStatusCode(response: Response, expected: Int) {
        assertEquals(expected, response.statusCode)
    }

    fun assertFieldEquals(response: Response, path: String, expected: Any) {
        assertEquals(expected, response.jsonPath(path))
    }
}
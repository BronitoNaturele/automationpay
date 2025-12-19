import dto.Request.UserRequest
import dto.Response.UserResponse

//Классы тестовых сценариев (Test Cases)
//UserApiTests, AuthApiTests и т. п.
//Содержат тестовые методы для конкретных эндпоинтов.
//Используют JUnit5 (@Test, @ParameterizedTest и др.).
//Вызывают методы ApiClient и проверяют ответы.

class UserApiTests {
    private val apiClient = ApiClient("https://api.example.com")

    @Test
    fun `create user successfully`() {
        val request = UserRequest("Alice", "alice@example.com")
        val response = apiClient.post("/users", request)

        assertEquals(201, response.statusCode)
        assertNotNull(response.body<UserResponse>().id)
    }
}
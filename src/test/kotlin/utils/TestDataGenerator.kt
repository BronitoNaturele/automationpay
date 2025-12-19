import dto.Request.UserRequest

//Классы для работы с тестовыми данными
//TestDataGenerator
//Генерирует случайные/валидные данные для тестов (имена, email, ID и т. п.).
//Позволяет избегать жёсткой привязки к конкретным значениям.

object TestDataGenerator {
    fun randomEmail(): String = "user${Random.nextInt(1000)}@example.com"
    fun validUserRequest(): UserRequest = UserRequest("Test User", randomEmail())
}
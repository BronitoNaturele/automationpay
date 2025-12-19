//Классы для работы с тестовыми данными
//utils.TestDataGenerator.TestDataGenerator
//Генерирует случайные/валидные данные для тестов (имена, email, ID и т. п.).
//Позволяет избегать жёсткой привязки к конкретным значениям.

package utils.TestDataGenerator

import dto.Request.UserRequest

object TestDataGenerator {
    fun randomEmail(): String = "user${Random.nextInt(1000)}@example.com"
    fun validUserRequest(): UserRequest = UserRequest("Test User", randomEmail())
}
//Базовые классы для тестов (опционально)
//BaseApiTest
//Содержит общую логику для всех тестовых классов (инициализация клиента, настройка заголовков и т. д.).
//Наследуется тестовыми сценариями.

open class BaseApiTest {
    protected val apiClient: ApiClient = ApiClient(TestConfig.baseUrl)
    protected val validator: ResponseValidator = ResponseValidator()
}
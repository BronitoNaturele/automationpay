//Классы для отправки HTTP‑запросов и получения ответов
//ApiClient (основной клиент для взаимодействия с API)
//Содержит методы для GET, POST, PUT, DELETE и др.
//Настраивает базовый URL, заголовки, авторизацию.
//Использует OkHttp, Ktor или RestAssured (в зависимости от выбранного HTTP‑клиента).

class ApiClient(private val baseUrl: String) {
    fun get(path: String, headers: Map<String, String> = emptyMap()): Response
    fun post(path: String, body: Any, headers: Map<String, String> = emptyMap()): Response
    // и т. д.
}
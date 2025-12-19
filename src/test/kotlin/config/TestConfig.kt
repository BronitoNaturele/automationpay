//Конфигурационные классы.
//Хранит базовые URL, токены, тайм‑ауты.

object TestConfig {
    val baseUrl: String = System.getenv("API_BASE_URL") ?: "https://localhost:8080"
    val authToken: String = System.getenv("API_TOKEN") ?: "dummy-token"
}
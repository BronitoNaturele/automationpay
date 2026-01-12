//Конфигурационные классы. Хранит базовые URL, токены, тайм‑ауты. authToken (он же API_TOKEN в файле Environment.kt) задаётся при запуске тестов, в консоли. Как и API_BASE_URL.
package config

class TestConfig(
    val baseUrl: String,
    val authToken: String,
    val timeoutSeconds: Int = 30
) {
    override fun toString(): String {
        return "TestConfig(baseUrl='$baseUrl', authToken='[REDACTED]')"
    }

    companion object {
        fun loadFromEnv(): TestConfig {
            // Дефолтные значения
            val defaultBaseUrl = "http://localhost:8080"
            val defaultToken = "dummy-token-for-local-tests"

            // Берём из ENV или используем дефолты
            val baseUrl = System.getenv("API_BASE_URL") ?: defaultBaseUrl
            val authToken = System.getenv("API_TOKEN") ?: defaultToken

            // Таймаут: из ENV или 30 секунд
            val timeoutSeconds = System.getenv("API_TIMEOUT_SECONDS")
                ?.toLongOrNull()
                ?.coerceAtLeast(1)  // Минимум 1 секунда
                ?.toInt()             // ← ключевое преобразование Long → Int
                ?: 30              // ← теперь 30 (Int), а не 30L (Long)

            return TestConfig(baseUrl, authToken, timeoutSeconds)
        }
    }
}
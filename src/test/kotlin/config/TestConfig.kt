//Конфигурационные классы.
//Хранит базовые URL, токены, тайм‑ауты.
//authToken (он же API_TOKEN в файле Environment.kt) задаётся при запуске тестов, в консоли. Как и API_BASE_URL.
package config

class TestConfig(
    val baseUrl: String,
    val authToken: String,
    val timeoutSeconds: Long = 30
) {
    override fun toString(): String {
        return "TestConfig(baseUrl='$baseUrl', authToken='[REDACTED]')"
    }

    companion object {
        fun loadFromEnv(): TestConfig {
            // Дефолтные значения (можно изменить под свой проект)
            val defaultBaseUrl = "http://localhost:8080"
            val defaultToken = "dummy-token-for-local-tests"

            // Пытаемся взять из ENV, иначе используем дефолты
            val baseUrl = System.getenv("API_BASE_URL") ?: defaultBaseUrl
            val authToken = System.getenv("API_TOKEN") ?: defaultToken

            // Таймаут: из ENV или 30 секунд
            val timeoutSeconds = System.getenv("API_TIMEOUT_SECONDS")
                ?.toLongOrNull()
                ?.coerceAtLeast(1)  // Минимум 1 секунда
                ?: 30L

            return TestConfig(baseUrl, authToken, timeoutSeconds)
        }
    }
}
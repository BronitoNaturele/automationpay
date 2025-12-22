package config

//Конфигурационные классы.
//Хранит базовые URL, токены, тайм‑ауты.
//authToken (он же API_TOKEN в файле Environment.kt) задаётся при запуске тестов, в консоли. Как и API_BASE_URL.
class TestConfig(
    val baseUrl: String,
    val authToken: String,
    val timeoutSeconds: Long = 30
) {
    override fun toString(): String {
        return "TestConfig(baseUrl='$baseUrl', authToken='[REDACTED]')"
    }
}
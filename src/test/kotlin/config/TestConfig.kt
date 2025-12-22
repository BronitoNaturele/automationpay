package config

//Конфигурационные классы.
//Хранит базовые URL, токены, тайм‑ауты.
//authToken (он же API_TOKEN в файле Environment.kt) задаётся при запуске тестов, в консоли.
class TestConfig(
    val baseUrl: String,
    val authToken: String
) {
    override fun toString(): String {
        return "TestConfig(baseUrl='$baseUrl', authToken='[REDACTED]')"
    }
}
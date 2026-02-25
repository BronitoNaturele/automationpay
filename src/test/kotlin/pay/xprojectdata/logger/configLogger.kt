import com.codeborne.selenide.Configuration
import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeAll
import org.slf4j.LoggerFactory


class configLogger {
    companion object {
        @JvmStatic // Генерирует статический метод в байт‑коде Java — JUnit 5 его распознаёт
        fun globalSetup() {
            val loggerInstance = configLogger()
            loggerInstance.startLogging()
        }
    }

    private  fun startLogging() {
        try {
            // Конфигурация Selenide
            Configuration.browser = System.getProperty("selenide.browser", "chrome")
            Configuration.headless = java.lang.Boolean.getBoolean("selenide.headless")
            Configuration.timeout = 10_000
            Configuration.screenshots = true
            Configuration.reportsFolder = "build/reports/selenide"
            Configuration.savePageSource = true

            // Логирование настроек Selenide
            logger.info("Selenide configuration: browser={}, headless={}, timeout={}ms",
                Configuration.browser, Configuration.headless, Configuration.timeout)

            // Конфигурация RestAssured
            val baseUrl = System.getProperty("api.base.url", "https://test.av.ru")
            RestAssured.baseURI = baseUrl
            RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()

            // Логирование настроек RestAssured
            logger.info("RestAssured configuration: baseURI={}", baseUrl)
        } catch (e: Exception) {
            logger.error("Failed to initialize test configuration", e)
            throw e
        }
    }
    private val logger = LoggerFactory.getLogger(configLogger::class.java)
}

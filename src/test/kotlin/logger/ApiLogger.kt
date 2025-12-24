//Логирование запросов/ответов (URL, заголовки, тело, статус).
//Настройка уровней логирования (DEBUG, INFO).
//Интеграция с SLF4J + Logback.
package config

import io.restassured.RestAssured.*
import org.junit.jupiter.api.Test

class ApiLogger {

    @Test
    fun fullLogs() {
        given().log().all()
    }
}

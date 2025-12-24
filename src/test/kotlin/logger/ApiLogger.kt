//Логирование запросов/ответов (URL, заголовки, тело, статус).
//Настройка уровней логирования (DEBUG, INFO).
//Интеграция с SLF4J + Logback.
package logger

import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.RestAssured

object ApiLogger {
    fun enableFullLogging() {
        RestAssured.filters(RequestLoggingFilter(), ResponseLoggingFilter())
    }

    fun disableLogging() {
        RestAssured.clearFilters()
    }
}
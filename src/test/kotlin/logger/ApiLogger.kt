//Логирование запросов/ответов (URL, заголовки, тело, статус).

package logger

import io.restassured.filter.log.LogDetail
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.RestAssured


object ApiLogger {

    /**
     * Включает глобальное логирование всех HTTP‑запросов и ответов
     * @param logBody true — логировать тело запроса/ответа, false — только заголовки
     * @param logHeaders true — включать заголовки в лог
     */
    fun enableLogging(
        logBody: Boolean = true,
        logHeaders: Boolean = true
    ) {
        val logDetail = when {
            logBody && logHeaders -> LogDetail.ALL
            logHeaders -> LogDetail.HEADERS
            logBody -> LogDetail.BODY
            else -> LogDetail.METHOD
        }

        val requestFilter = RequestLoggingFilter(logDetail, logBody, System.out)
        val responseFilter = ResponseLoggingFilter(logDetail, logBody, System.out)

        RestAssured.filters(requestFilter, responseFilter)
    }

    /**
     * Отключает все глобальные фильтры логирования
     */
    fun disableLogging() {
        RestAssured.filters()
    }

    /**
     * Возвращает фильтр для логирования запроса (для использования в конкретных тестах)
     * @param logDetail уровень детализации логирования
     * @param logBody включать ли тело в лог
     */
    fun requestFilter(
        logDetail: LogDetail = LogDetail.ALL,
        logBody: Boolean = true
    ): RequestLoggingFilter {
        return RequestLoggingFilter(logDetail, logBody, System.out)
    }

    /**
     * Возвращает фильтр для логирования ответа (для использования в конкретных тестах)
     * @param logDetail уровень детализации логирования
     * @param logBody включать ли тело в лог
     */
    fun responseFilter(
        logDetail: LogDetail = LogDetail.ALL,
        logBody: Boolean = true
    ): ResponseLoggingFilter {
        return ResponseLoggingFilter(logDetail, logBody, System.out)
    }
}
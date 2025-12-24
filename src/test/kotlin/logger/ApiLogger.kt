//Логирование запросов/ответов (URL, заголовки, тело, статус).
//Настройка уровней логирования (DEBUG, INFO).
//Интеграция с SLF4J + Logback.
package logger

import config.TestConfig

import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType

object ApiLogger {
    fun enableFullRequestLogging() {
        given()
            .log().all()  // Включаем полное логирование запроса
            .baseUri("https://test-pay.av.ru")
            .basePath("/api/v1/payment/methods")
            .contentType(ContentType.JSON)
            .header("X-API-Key", "your-api-key")
            .queryParam("limit", 10)
        .when()
            .get()
        .then()
            .statusCode(200)
    }
    }
    fun enableFullResponseLogging() {
        RestAssured.filters(RequestLoggingFilter(), ResponseLoggingFilter())
    }
}


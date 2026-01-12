//Классы тестовых сценариев. Вызывают методы ApiClient и проверяют ответы.

package tests

import io.restassured.response.Response
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.AfterEach

import client.ApiClient
import config.EnvironmentConfig
import dto.Request.SberGateRequestGenerator
import io.restassured.module.jsv.JsonSchemaValidator
import utils.JsonUtils.JsonUtils


class POST_api_v1_payment_pay_method_uuid_Sber_Gate {
    private lateinit var apiClient: ApiClient

    @BeforeEach
    fun setUp() {
        val config = EnvironmentConfig.getConfigFromEnvVar()
        apiClient = ApiClient(config)
        //ApiLogger.enableLogging(logBody = true) // Включаем полное логирование
    }
    @AfterEach
    fun tearDown() {
        //ApiLogger.disableLogging()
    }

    @Test
    //Проверяем ответ на запрос, чтобы он соответствовал схеме JSON
    fun `Validating the JSON scheme to the response`() {
        // 1. Подготовка запроса
        val request = SberGateRequestGenerator.baseRequest()

        // 2. Выполнение запроса и получение ответа
        val response: Response = apiClient
            .body(request)
            .post("/api/v1/payment/pay/method_uuid")

        // 3. Валидация ответа (единая цепочка проверок)
        response.then()
            .log().all()                          // Логируем всё о запросе/ответе
            .statusCode(202)                     // Проверяем статус-код
            .body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath(
                    "JsonSchema/POST_api_v1_payment_pay_method_uuid_Sber_Gate.json"
                )
            )

        // 4. Дополнительная проверка (опционально, т.к. статус уже проверен выше)
        // Можно убрать, если доверяете Rest-Assured
        assertEquals(
            202,
            response.statusCode,
            "Ожидался код 202, но получен ${response.statusCode}: ${response.asString()}"
        )
    }

    @Test
    fun `new`() {

    }

}
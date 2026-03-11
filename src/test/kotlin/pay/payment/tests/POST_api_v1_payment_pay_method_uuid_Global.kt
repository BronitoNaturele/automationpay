package pay.payment.tests

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.restassured.response.Response
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import pay.xprojectdata.client.ApiClient
import pay.xprojectdata.config.EnvironmentConfig
import pay.xprojectdata.dto.request.SberGateRequestGenerator
import pay.xprojectdata.dto.request.errorNoMethodPayMethodUuidResponseBodyGlobal
import pay.xprojectdata.dto.request.errorPayMethodUuidResponseBodyGlobal
import ru.testit.annotations.WorkItemIds

class POSTApiV1PaymentPayMethodUuidGlobal {
    private val mapper = jacksonObjectMapper()
    private lateinit var apiClient: ApiClient

    companion object {
        @BeforeAll
        @JvmStatic
        fun globalSetup() {
            configLogger.globalSetup()
        }
    }

    @BeforeEach
    fun setUp() {
        val config = EnvironmentConfig.getConfigFromEnvVar()
        apiClient = ApiClient(config)
        //ApiLogger.enableLogging(logBody = true)
    }

    @AfterEach
    fun tearDown() {
        try {
            // Закрываем HTTP‑соединения
            io.restassured.RestAssured.reset()

            // Принудительное завершение потоков (если есть кастомные пулы)
            // ExecutorService?.shutdown()
        } catch (e: Exception) {
            println("Ошибка при очистке: ${e.message}")
        }
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("9a4b67b2-7661-420f-afcf-dd291cf18944")
    @DisplayName("405. Не передан method_uuid в url")
    fun noMethodUuidSberGate() {
        val requestBody = SberGateRequestGenerator.baseRequest()
        //Выполнение POST-запроса
        val response: Response = apiClient.post(
            path = "/api/v1/payment/pay/",
            body = requestBody
        )
        // Валидируем статус
        response.then().statusCode(405)

        // Парсим в DTO
        val paymentResponse: errorNoMethodPayMethodUuidResponseBodyGlobal = mapper.readValue(
            response.asString(),
            errorNoMethodPayMethodUuidResponseBodyGlobal::class.java
        )

        // Проверяем поля с помощью JUnit assertions
        assertEquals(
            "The POST method is not supported for route api/v1/payment/pay. Supported methods: GET, HEAD.",
            paymentResponse.error_message,
            "Поле error_message должно содержать сообщение об ошибке авторизации"
        )
        assertEquals(
            "405",
            paymentResponse.error_code,
            "Поле error_code должно быть равно 405"
        )
        assertEquals(
            "METHOD_NOT_ALLOWED",
            paymentResponse.type_error,
            "Поле type_error должно быть равно METHOD_NOT_ALLOWED"
        )
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("f4c9eb2f-8270-40fa-8be1-19fd697ba794")
    @DisplayName("406. Не валидный method_uuid в url")
    fun notValidMethodUuidSberGate() {
        val requestBody = SberGateRequestGenerator.baseRequest()
        val response: Response = apiClient.post(
            path = "/api/v1/payment/pay/123",
            body = requestBody
        )
        response.then().statusCode(406)

        // Парсим в DTO
        val paymentResponse: errorPayMethodUuidResponseBodyGlobal = mapper.readValue(
            response.asString(),
            errorPayMethodUuidResponseBodyGlobal::class.java
        )

        // Проверяем поля с помощью JUnit assertions
        assertEquals(
            "Данный метод недоступен для платежа",
            paymentResponse.error_message,
            "Поле error_message должно содержать сообщение об ошибке авторизации"
        )
        assertEquals(
            "S0.000005",
            paymentResponse.error_code,
            "Поле error_code должно быть равно S0.000005"
        )
        assertEquals(
            "NOT_ACCEPTABLE",
            paymentResponse.type_error,
            "Поле type_error должно быть равно NOT_ACCEPTABLE"
        )
    }
}
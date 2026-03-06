package pay.payment.tests

import io.restassured.RestAssured
import io.restassured.response.Response
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasItem

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Tag

import pay.xprojectdata.client.ApiClient
import pay.xprojectdata.config.EnvironmentConfig
import pay.xprojectdata.dto.request.globalBodyRequestForConfirmPutRequestGenerator
import ru.testit.annotations.WorkItemIds

class PUTApiV1PaymentConfirmGlobal {
    private lateinit var apiClient: ApiClient

    // Логгер
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
    @WorkItemIds("1272968c-0ec6-4d50-a243-1a1ee6225df4")
    @DisplayName("401. Ошибка авторизации. Нет токена")
    fun noTokenConfirmGlobal() {
        val modifiedRequestBodyPut = globalBodyRequestForConfirmPutRequestGenerator.baseRequest()
        val responseNoToken = RestAssured
            .given()
            .body(modifiedRequestBodyPut)
            .put("/api/v1/payment/confirm")

        responseNoToken
            .then()
            .statusCode(401)
            .body("error_message", equalTo("Ошибка авторизации или данная операция запрещена правами доступа"))
            .body("error_code", equalTo("S0.000002"))
            .body("type_error", equalTo("UNAUTHORIZED"))
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("1272968c-0ec6-4d50-a243-1a1ee6225df4")
    @DisplayName("401. Ошибка авторизации. Невалидный токен")
    fun noValidTokenConfirmGlobal() {
        val modifiedRequestBodyPut = globalBodyRequestForConfirmPutRequestGenerator.baseRequest()
        val responseNoToken = RestAssured
            .given()
            .body(modifiedRequestBodyPut)
            .header("Authorization", "Bearer e37ABDDy9HTV0gFoX1uCwdld9uSSjEYlrV7v0qrs2OfZOONm223XLMLKasdasd9GyPDMJFpmMIQLSPkG9XfCzT")
            .put("/api/v1/payment/confirm")

        responseNoToken
            .then()
            .statusCode(401)
            .body("error_message", equalTo("Ошибка авторизации или данная операция запрещена правами доступа"))
            .body("error_code", equalTo("S0.000002"))
            .body("type_error", equalTo("UNAUTHORIZED"))
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("924aa6bb-d647-48ac-8fd8-945855c957af")
    @DisplayName("404. Транзакция не найдена")
    fun notFoundErrorConfirmGlobal() {
        val basePathForPut = "/api/v1/payment/confirm"
        val modifiedRequestBodyPut = globalBodyRequestForConfirmPutRequestGenerator.baseRequest().copy(
            amount = 2,
            id = 1234232332
        )
        val responsePut: Response = apiClient.put(
            path = basePathForPut,
            body = modifiedRequestBodyPut
        )
        responsePut.then()
            .statusCode(404)
            .body("error_message", equalTo("Транзакция не найдена"))
            .body("error_code", equalTo("S0.000011"))
            .body("type_error", equalTo("NOT_FOUND"))

    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("a4667106-6650-4669-99df-ef65e6c7de8f")
    @DisplayName("422. Проверка обязательности полей. Не передаём id")
    fun noIdErrorConfirmGlobal() {
        val basePathForPut = "/api/v1/payment/confirm"
        val modifiedRequestBodyPut = globalBodyRequestForConfirmPutRequestGenerator.noIdRequest().copy()
        val responsePut: Response = apiClient.put(
            path = basePathForPut,
            body = modifiedRequestBodyPut
        )
        responsePut.then()
            .statusCode(422)
            .body("error_code", equalTo(422))
            .body("error_message", equalTo("Не верные переданные данные в апи"))
            .body("type_error", equalTo("UNPROCESSABLE_CONTENT"))
            .body("errors.id[0]", equalTo("Поле id обязательно для заполнения."))

    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("a4667106-6650-4669-99df-ef65e6c7de8f")
    @DisplayName("422. Проверка обязательности полей. Не передаём amount")
    fun noAmountErrorConfirmGlobal() {
        val basePathForPut = "/api/v1/payment/confirm"
        val modifiedRequestBodyPut = globalBodyRequestForConfirmPutRequestGenerator.noAmountRequest().copy()
        val responsePut: Response = apiClient.put(
            path = basePathForPut,
            body = modifiedRequestBodyPut
        )
        responsePut.then()
            .statusCode(422)
            .body("error_code", equalTo(422))
            .body("error_message", equalTo("Не верные переданные данные в апи"))
            .body("type_error", equalTo("UNPROCESSABLE_CONTENT"))
            .body("errors.amount[0]", equalTo("Поле amount обязательно для заполнения."))

    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("a4667106-6650-4669-99df-ef65e6c7de8f")
    @DisplayName("422. Валидации по типу данных. Передаём id = 1.1")
    fun idFloatErrorConfirmGlobal() {
        val basePathForPut = "/api/v1/payment/confirm"
        val modifiedRequestBodyPut = globalBodyRequestForConfirmPutRequestGenerator.baseRequest().copy(
            id = 1.1
        )
        val responsePut: Response = apiClient.put(
            path = basePathForPut,
            body = modifiedRequestBodyPut
        )
        responsePut.then()
            .statusCode(422)
            .body(
                "error_code", equalTo(422),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("UNPROCESSABLE_CONTENT"),
                "errors.id[0]", equalTo("Поле id должно быть целым числом.")
            )

    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("a4667106-6650-4669-99df-ef65e6c7de8f")
    @DisplayName("422. Валидации по типу данных. Передаём id = “1a!”")
    fun idStringErrorConfirmGlobal() {
        val basePathForPut = "/api/v1/payment/confirm"
        val modifiedRequestBodyPut = globalBodyRequestForConfirmPutRequestGenerator.baseRequest().copy(
            id = "1a!"
        )
        val responsePut: Response = apiClient.put(
            path = basePathForPut,
            body = modifiedRequestBodyPut
        )
        responsePut.then()
            .statusCode(422)
            .body(
                "error_code", equalTo(422),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("UNPROCESSABLE_CONTENT"),
                "errors.id[0]", equalTo("Поле id должно быть целым числом.")
            )

    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("a4667106-6650-4669-99df-ef65e6c7de8f")
    @DisplayName("422. Валидации по типу данных. Передаём id = {}")
    fun idObjectErrorConfirmGlobal() {
        val basePathForPut = "/api/v1/payment/confirm"
        val modifiedRequestBodyPut = globalBodyRequestForConfirmPutRequestGenerator.baseRequest().copy(
            id = {}
        )
        val responsePut: Response = apiClient.put(
            path = basePathForPut,
            body = modifiedRequestBodyPut
        )
        responsePut.then()
            .statusCode(422)
            .body(
                "error_code", equalTo(422),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("UNPROCESSABLE_CONTENT"),
                "errors.id[0]", equalTo("Поле id обязательно для заполнения.")
            )

    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("a4667106-6650-4669-99df-ef65e6c7de8f")
    @DisplayName("422. Валидации по типу данных. Передаём id = []")
    fun idEmptyArrayErrorConfirmGlobal() {
        val basePathForPut = "/api/v1/payment/confirm"
        val modifiedRequestBodyPut = globalBodyRequestForConfirmPutRequestGenerator.baseRequest().copy(
            id = emptyArray<Any?>()
        )
        val responsePut: Response = apiClient.put(
            path = basePathForPut,
            body = modifiedRequestBodyPut
        )
        responsePut.then()
            .statusCode(422)
            .body(
                "error_code", equalTo(422),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("UNPROCESSABLE_CONTENT"),
                "errors.id[0]", equalTo("Поле id обязательно для заполнения.")
            )

    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("a4667106-6650-4669-99df-ef65e6c7de8f")
    @DisplayName("422. Валидации по типу данных. Передаём id = false")
    fun idBooleanErrorConfirmGlobal() {
        val basePathForPut = "/api/v1/payment/confirm"
        val modifiedRequestBodyPut = globalBodyRequestForConfirmPutRequestGenerator.baseRequest().copy(
            id = false
        )
        val responsePut: Response = apiClient.put(
            path = basePathForPut,
            body = modifiedRequestBodyPut
        )
        responsePut.then()
            .statusCode(422)
            .body(
                "error_code", equalTo(422),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("UNPROCESSABLE_CONTENT"),
                "errors.id[0]", equalTo("Поле id должно быть целым числом.")
            )

    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("a4667106-6650-4669-99df-ef65e6c7de8f")
    @DisplayName("422. Валидации по типу данных. Передаём id = null")
    fun idNullErrorConfirmGlobal() {
        val basePathForPut = "/api/v1/payment/confirm"
        val modifiedRequestBodyPut = globalBodyRequestForConfirmPutRequestGenerator.baseRequest().copy(
            id = null
        )
        val responsePut: Response = apiClient.put(
            path = basePathForPut,
            body = modifiedRequestBodyPut
        )
        responsePut.then()
            .statusCode(422)
            .body(
                "error_code", equalTo(422),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("UNPROCESSABLE_CONTENT"),
                "errors.id[0]", equalTo("Поле id обязательно для заполнения.")
            )

    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("a4667106-6650-4669-99df-ef65e6c7de8f")
    @DisplayName("422. Валидации по типу данных. Передаём amount = '1a!'")
    fun amountStringErrorConfirmGlobal() {
        val basePathForPut = "/api/v1/payment/confirm"
        val modifiedRequestBodyPut = globalBodyRequestForConfirmPutRequestGenerator.baseRequest().copy(
            id = 1234567,
            amount = "1a!"
        )
        val responsePut: Response = apiClient.put(
            path = basePathForPut,
            body = modifiedRequestBodyPut
        )
        responsePut.then()
            .statusCode(422)
            .body(
                "error_code", equalTo(422),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("UNPROCESSABLE_CONTENT"),
                "errors.amount[0]", equalTo("Поле amount должно быть числом.")
            )

    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("a4667106-6650-4669-99df-ef65e6c7de8f")
    @DisplayName("422. Валидации по типу данных. Передаём amount = {}")
    fun amountObjectErrorConfirmGlobal() {
        val basePathForPut = "/api/v1/payment/confirm"
        val modifiedRequestBodyPut = globalBodyRequestForConfirmPutRequestGenerator.baseRequest().copy(
            id = 1234567,
            amount = {}
        )
        val responsePut: Response = apiClient.put(
            path = basePathForPut,
            body = modifiedRequestBodyPut
        )
        responsePut.then()
            .statusCode(422)
            .body(
                "error_code", equalTo(422),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("UNPROCESSABLE_CONTENT"),
                "errors.amount[0]", equalTo("Поле amount обязательно для заполнения.")
            )

    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("a4667106-6650-4669-99df-ef65e6c7de8f")
    @DisplayName("422. Валидации по типу данных. Передаём amount = []")
    fun amountEmptyArrayErrorConfirmGlobal() {
        val basePathForPut = "/api/v1/payment/confirm"
        val modifiedRequestBodyPut = globalBodyRequestForConfirmPutRequestGenerator.baseRequest().copy(
            id = 1234567,
            amount = emptyArray<Any?>()
        )
        val responsePut: Response = apiClient.put(
            path = basePathForPut,
            body = modifiedRequestBodyPut
        )
        responsePut.then()
            .statusCode(422)
            .body(
                "error_code", equalTo(422),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("UNPROCESSABLE_CONTENT"),
                "errors.amount[0]", equalTo("Поле amount обязательно для заполнения.")
            )

    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("a4667106-6650-4669-99df-ef65e6c7de8f")
    @DisplayName("422. Валидации по типу данных. Передаём amount = false")
    fun amountBooleanErrorConfirmGlobal() {
        val basePathForPut = "/api/v1/payment/confirm"
        val modifiedRequestBodyPut = globalBodyRequestForConfirmPutRequestGenerator.baseRequest().copy(
            id = 1234567,
            amount = false
        )
        val responsePut: Response = apiClient.put(
            path = basePathForPut,
            body = modifiedRequestBodyPut
        )
        responsePut.then()
            .statusCode(422)
            .body(
                "error_code", equalTo(422),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("UNPROCESSABLE_CONTENT"),
                "errors.amount[0]", equalTo("Поле amount должно быть числом.")
            )

    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @WorkItemIds("a4667106-6650-4669-99df-ef65e6c7de8f")
    @DisplayName("422. Валидации по типу данных. Передаём amount = null")
    fun amountNullErrorConfirmGlobal() {
        val basePathForPut = "/api/v1/payment/confirm"
        val modifiedRequestBodyPut = globalBodyRequestForConfirmPutRequestGenerator.baseRequest().copy(
            id = 1234567,
            amount = null
        )
        val responsePut: Response = apiClient.put(
            path = basePathForPut,
            body = modifiedRequestBodyPut
        )
        responsePut.then()
            .statusCode(422)
            .body(
                "error_code", equalTo(422),
                "error_message", equalTo("Не верные переданные данные в апи"),
                "type_error", equalTo("UNPROCESSABLE_CONTENT"),
                "errors.amount[0]", equalTo("Поле amount обязательно для заполнения.")
            )

    }
}
package tests

import client.ApiClient
import config.EnvironmentConfig
import dto.Request.SberGateRequestGenerator
import io.restassured.module.jsv.JsonSchemaValidator
import io.restassured.response.Response
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.WebDriver



class PUT_api_v1_payment_confirm_SberGate {
    private lateinit var apiClient: ApiClient
    private lateinit var driver: WebDriver
    val basePath = "/api/v1/payment/pay/d96d0e7f-771a-4c85-9f13-5eda4bca9251"


    @BeforeEach
    fun setUp() {
        val config = EnvironmentConfig.getConfigFromEnvVar()
        apiClient = ApiClient(config)
        WebDriverManager.chromedriver().setup()
        driver = ChromeDriver()
        //ApiLogger.enableLogging(logBody = true) // Включаем полное логирование
    }
    @AfterEach
    fun tearDown() {
        //ApiLogger.disableLogging()
        if (driver != null) {
            driver.quit()
        }
    }

    @Test
    fun `202 - Validating the JSON scheme to the response with method_uuid`() {
        //Подготовка тела запроса
        val requestBody = SberGateRequestGenerator.baseRequest()
        //Выполнение POST-запроса
        val response: Response = apiClient.post(
            path = basePath,
            body = requestBody,
            headers = emptyMap() // если нужны дополнительные заголовки — передайте их
        )

        //Валидация ответа (единая цепочка)
        response.then()
            .statusCode(202) // Проверяем статус-код
            .body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath(
                    "JsonSchema/POST_api_v1_payment_pay_method_uuid_Sber_Gate.json"
                )
            )
    }
}
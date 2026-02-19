package tests

import client.ApiClient
import com.codeborne.selenide.Condition
import config.EnvironmentConfig
import dto.Request.SberGateRequestGenerator

import com.codeborne.selenide.Selenide.open
import com.codeborne.selenide.Selenide.*
import com.codeborne.selenide.SelenideElement
import io.restassured.module.jsv.JsonSchemaValidator
import io.restassured.response.Response

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

class PUT_api_v1_payment_confirm_SberGate {
    private lateinit var apiClient: ApiClient
        val basePath = "/api/v1/payment/pay/d96d0e7f-771a-4c85-9f13-5eda4bca9251"


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
    fun `202 - Успешное проведение платежа Картой СГ`() {
        //Подготовка тела запроса
        val requestBody = SberGateRequestGenerator.baseRequest()
        //Выполнение POST-запроса
        val response: Response = apiClient.post(
            path = basePath,
            body = requestBody,
            headers = emptyMap() // если нужны дополнительные заголовки — передайте их
        )

        //Валидация ответа и извлечение url
        val jsonPath = response.then()
            .statusCode(202)
            .body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath(
                    "JsonSchema/POST_api_v1_payment_pay_method_uuid_Sber_Gate.json"
                )
            )
            .extract() //Извлекаем ответ после валидации
            .jsonPath() //Получаем JsonPath

        val extractedUrl = jsonPath.getString("url")
        val extractedId = jsonPath.getString("id")

        println("Извлечённый URL: $extractedUrl")
        println("Извлечённый ID: $extractedId")

        assertNotNull(extractedUrl, "Поле url = Null")
        assertTrue(extractedUrl.isNotEmpty(), "Поле url пустое")

        assertNotNull(extractedId, "Поле id = Null")
        assertTrue(extractedId.isNotEmpty(), "Поле id пустое")

        //Открываем страницу по извлечённому URL
        open(extractedUrl)
        // Находим элементы на странице и вводим данные
        val elementCardNumber: SelenideElement = `$`("input[autocomplete='cc-number']").setValue("2202205000012424")//Карта без ACS кода
        elementCardNumber.should(Condition.exist)
        elementCardNumber.click()

        val elementCardName: SelenideElement = `$`("input[autocomplete='cc-name']").setValue("zxc zxc")
        elementCardName.should(Condition.exist)
        elementCardName.click()

        val elementCardExpiration: SelenideElement = `$`("input[autocomplete='cc-exp']").setValue("0535")
        elementCardExpiration.should(Condition.exist)
        elementCardExpiration.click()

        val elementCardCVV: SelenideElement = `$`(".inputs_back_cvv input").setValue("669")
        elementCardCVV.should(Condition.exist)
        elementCardCVV.click()

        val elementButtonSubmit: SelenideElement = `$`(".footer_buttons button")
        elementButtonSubmit.should(Condition.exist)
        elementButtonSubmit.click()

        //Тут надо будет дописать(когда будет готово) запрос на проверку статуса транзакции
    }
}
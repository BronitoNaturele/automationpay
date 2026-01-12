////Классы тестовых сценариев. Вызывают методы ApiClient и проверяют ответы.
//
//package tests
//
//import io.restassured.response.Response
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.Assertions.*
//import org.junit.jupiter.api.AfterEach
//
//import client.ApiClient
//import config.EnvironmentConfig
//import dto.Request.BodyPaymentMethodsResponse
//import io.restassured.module.jsv.JsonSchemaValidator
//import utils.JsonUtils.JsonUtils
//import java.util.Random
//
//class POST_api_v1_payment_pay_method_uuid_Sber_Gate {
//    private lateinit var apiClient: ApiClient
//
//    val random = Random()
//    // Генерируем рандомное 10ти значное число
//    val random10DigitNumber: Long = random.nextLong(1000000000, 1000000000)
//
//    val requestBody = mapOf(
//        "amount" to 2,
//        "invoice_id" to random,
//        "mobile" to false,
//        "platform" to "WEB",
//        "account_id"
//    )
//
//    @BeforeEach
//    fun setUp() {
//        val config = EnvironmentConfig.getConfigFromEnvVar()
//        apiClient = ApiClient(config)
//        //ApiLogger.enableLogging(logBody = true) // Включаем полное логирование
//    }
//    @AfterEach
//    fun tearDown() {
//        //ApiLogger.disableLogging()
//    }
//
//    @Test
//    //Проверяем ответ на запрос, чтобы он соответствовал схеме JSON
//    fun `Validating the JSON scheme to the response`() {
//        val response: Response = apiClient.post("/api/v1/payment/pay/method_uuid")
//        response.then()
//            .log().all()
//        response
//            .then()
//            .statusCode(202)
//            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("JsonSchema/POST_api_v1_payment_pay_method_uuid_Sber_Gate.json"))
//
//        assertEquals(202, response.statusCode) {
//            "Ожидался код 202, но получен ${response.statusCode}: ${response.asString()}"
//        }
//    }
//
//    @Test
//    fun `new`() {
//
//    }
//
//}
package pay.payment.tests

import io.restassured.path.json.JsonPath
import io.restassured.response.Response
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import pay.xprojectdata.client.ApiClient
import pay.xprojectdata.config.EnvironmentConfig

class DELETEApiV1PaymentCancelId {
    private lateinit var apiClient: ApiClient
    private var extractedId = ""

    // Валидация Json
    private fun getJsonPath(response: Response): JsonPath {
        val json = response.asString()
        return JsonPath.from(json)
    }

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

        //ApiLogger.enableLogging(logBody = true) // Включаем полное логирование
    }

    @AfterEach
    fun tearDown() {
        //ApiLogger.disableLogging()

    }
}
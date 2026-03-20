package pay.xprojectdata.dto.request

import com.fasterxml.jackson.annotation.JsonInclude
import kotlin.random.Random

//Класс-конструктор тела запроса без возможности отправить null в полях
@JsonInclude(JsonInclude.Include.NON_NULL)
data class SmBodyRequest(
    val amount: Any? = null,
    val invoice_id: Any? = null,
    val mobile: Any? = null,
    val platform: Any? = null,
    val account_id: Any? = null,
    val secure_pay: Any? = null,
    val name: Any? = null,
    val payload: Any? = null,
    val phone: Any? = null,
    val email: Any? = null,
    val description: Any? = null,
    val fields: Any? = null
)

data class SmPayloadItem(
    val key: Any? = null,
    val value: Any? = null
)

data class SmFields(
    val ttl: Any? = null
)

object SmRequestGenerator {
    val securePayFromSystem = System.getProperty("securePayValue")
    private val random = Random

    // Генерирует случайное 10‑значное число
    fun generateRandom10Digit(): Long {
        return (1e9.toLong()..9999999999L).random(random)
    }

    // Базовый шаблон запроса (можно расширять)
    fun baseRequest(): SmBodyRequest {
        return SmBodyRequest(
            amount = "2",
            invoice_id = generateRandom10Digit().toString(),
            mobile = false,
            platform = "WEB",
            account_id = "1-2Q4PVM7Z",
            secure_pay = securePayFromSystem,
            name = "randomName",
            payload = listOf(
                SmPayloadItem(
                    key = "testKey",
                    value = "testValue")
            ),
            phone = "79000000000",
            email = "klepa_e@mail.ru",
            description = "TestDeadCow",
            fields = SmFields(
                ttl = 3600
            )
        )
    }

    fun noAccountIdRequest(): SmBodyRequest {
        return SmBodyRequest(
            amount = "2",
            invoice_id = generateRandom10Digit().toString(),
            mobile = false,
            platform = "WEB",
            secure_pay = securePayFromSystem,
            name = "randomName",
            payload = listOf(
                SmPayloadItem(
                    key = "testKey",
                    value = "testValue")
            ),
            phone = "79000000000",
            email = "klepa_e@mail.ru",
            description = "TestDeadCow",
            fields = SmFields(
                ttl = 3600
            )
        )
    }

    fun noSecurePayRequest(): SmBodyRequest {
        return SmBodyRequest(
            amount = "2",
            invoice_id = generateRandom10Digit().toString(),
            mobile = false,
            platform = "WEB",
            account_id = "1-2Q4PVM7Z",
            name = "randomName",
            payload = listOf(
                SmPayloadItem(
                    key = "testKey",
                    value = "testValue")
            ),
            phone = "79000000000",
            email = "klepa_e@mail.ru",
            description = "TestDeadCow",
            fields = SmFields(
                ttl = 3600
            )
        )
    }

    fun noMobileRequest(): SmBodyRequest {
        return SmBodyRequest(
            amount = "2",
            invoice_id = generateRandom10Digit().toString(),
            platform = "WEB",
            account_id = "1-2Q4PVM7Z",
            secure_pay = securePayFromSystem,
            name = "randomName",
            payload = listOf(
                SmPayloadItem(
                    key = "testKey",
                    value = "testValue")
            ),
            phone = "79000000000",
            email = "klepa_e@mail.ru",
            description = "TestDeadCow",
            fields = SmFields(
                ttl = 3600
            )
        )
    }

    fun noPlatformRequest(): SmBodyRequest {
        return SmBodyRequest(
            amount = "2",
            invoice_id = generateRandom10Digit().toString(),
            mobile = false,
            account_id = "1-2Q4PVM7Z",
            secure_pay = securePayFromSystem,
            name = "randomName",
            payload = listOf(
                SmPayloadItem(
                    key = "testKey",
                    value = "testValue")
            ),
            phone = "79000000000",
            email = "klepa_e@mail.ru",
            description = "TestDeadCow",
            fields = SmFields(
                ttl = 3600
            )
        )
    }

    fun noAmountRequest(): SmBodyRequest {
        return SmBodyRequest(
            invoice_id = generateRandom10Digit().toString(),
            mobile = false,
            platform = "WEB",
            account_id = "1-2Q4PVM7Z",
            secure_pay = securePayFromSystem,
            name = "randomName",
            payload = listOf(
                SmPayloadItem(
                    key = "testKey",
                    value = "testValue")
            ),
            phone = "79000000000",
            email = "klepa_e@mail.ru",
            description = "TestDeadCow",
            fields = SmFields(
                ttl = 3600
            )
        )
    }

    fun noInvoiceIdRequest(): SmBodyRequest {
        return SmBodyRequest(
            amount = "2",
            mobile = false,
            platform = "WEB",
            account_id = "1-2Q4PVM7Z",
            secure_pay = securePayFromSystem,
            name = "randomName",
            payload = listOf(
                SmPayloadItem(
                    key = "testKey",
                    value = "testValue")
            ),
            phone = "79000000000",
            email = "klepa_e@mail.ru",
            description = "TestDeadCow",
            fields = SmFields(
                ttl = 3600
            )
        )
    }


}

//Класс-конструктор тела запроса с возможностью отправить null в полях
data class SmBodyNullFieldsRequest(
    val amount: Any? = null,
    val invoice_id: Any? = null,
    val mobile: Any? = null,
    val platform: Any? = null,
    val account_id: Any? = null,
    val secure_pay: Any? = null,
    val name: Any? = null,
    val payload: Any? = null,
    val phone: Any? = null,
    val email: Any? = null,
    val description: Any? = null,
    val fields: Any? = null
)

data class SmNullPayloadItem(
    val key: Any? = null,
    val value: Any? = null
)

data class SmNullFields(
    val ttl: Any? = null
)

object SmNullFieldsRequestGenerator {
    val securePayFromSystem = System.getProperty("securePayValue")
    private val random = Random

    // Генерирует случайное 10‑значное число
    fun generateRandom10Digit(): Long {
        return (1e9.toLong()..9999999999L).random(random)
    }

    // Базовый шаблон запроса (можно расширять)
    fun baseRequest(): SmBodyNullFieldsRequest {
        return SmBodyNullFieldsRequest(
            amount = "2",
            invoice_id = generateRandom10Digit().toString(),
            mobile = false,
            platform = "WEB",
            account_id = "1-2Q4PVM7Z",
            secure_pay = securePayFromSystem,
            name = "randomName",
            payload = listOf(
                SmNullPayloadItem(
                    key = "testKey",
                    value = "testValue")
            ),
            phone = "79000000000",
            email = "klepa_e@mail.ru",
            description = "TestDeadCow",
            fields = SmNullFields(
                ttl = 3600
            )
        )
    }

    // Формирует URL с query-параметром method_uuid
    fun buildUrlWithMethodUuid(basePath: String, methodUuid: String): String {
        return "$basePath?method_uuid=$methodUuid"
    }
}

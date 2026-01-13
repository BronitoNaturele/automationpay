package dto.Request

import kotlin.random.Random

data class SberGateBodyRequest(
    val amount: String,
    val invoice_id: String,
    val mobile: Boolean,
    val platform: String,
    val account_id: String,
    //val secure_pay: String? = null,
    val name: String,
    val payload: List<PayloadItem>,
    val phone: String,
    val email: String,
    val description: String,
    val fields: Fields
)

data class PayloadItem(
    val key: String,
    val value: String
)

data class Fields(
    val successUrl: String,
    val errorUrl: String,
    val ttl: Int
)

// Вспомогательный объект для генерации тестовых данных
object SberGateRequestGenerator {

    private val random = Random

    // Генерирует случайное 10‑значное число
    fun generateRandom10Digit(): Long {
        return (1e9.toLong()..9999999999L).random(random)
    }

    // Базовый шаблон запроса (можно расширять)
    fun baseRequest(): SberGateBodyRequest {
        return SberGateBodyRequest(
            amount = "2",
            invoice_id = generateRandom10Digit().toString(),
            mobile = false,
            platform = "WEB",
            account_id = "1-2Q4PVM7Z",
            //secure_pay = null,
            name = "randomName",
            payload = listOf(PayloadItem("testKey", "testValue")),
            phone = "79138166920",
            email = "klepa_e@mail.ru",
            description = "TestDeadCow",
            fields = Fields(
                successUrl = "https://uat-pay.av.ru/success/123456",
                errorUrl = "https://uat-pay.av.ru//error/123456",
                ttl = 3600
            )
        )
    }

    // Создаёт запрос с изменёнными полями
    fun requestWith(
        amount: String = "2",
        invoice_id: String = generateRandom10Digit().toString(),
        mobile: Boolean = false,
        platform: String = "WEB",
        account_id: String = "1-2Q4PVM7Z",
        name: String = "randomName"
    ): SberGateBodyRequest {
        return baseRequest().copy(
            amount = amount,
            invoice_id = invoice_id,
            mobile = mobile,
            platform = platform,
            account_id = account_id,
            name = name
        )
    }

    // Формирует URL с query-параметром method_uuid
    fun buildUrlWithMethodUuid(basePath: String, methodUuid: String): String {
        return "$basePath?method_uuid=$methodUuid"
    }
}
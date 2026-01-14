package dto.Request

import com.fasterxml.jackson.annotation.JsonInclude
import kotlin.random.Random

//Класс-конструктор тела запроса без возможности отправить null в полях
@JsonInclude(JsonInclude.Include.NON_NULL)
data class SberGateBodyRequest(
    val amount: Any? = null,
    val invoice_id: Any? = null,
    val mobile: Any? = null,
    val platform: Any? = null,
    val account_id: Any? = null,
    //val secure_pay: String? = null,
    val name: Any? = null,
    val payload: Any? = null,
    val phone: Any? = null,
    val email: Any? = null,
    val description: Any? = null,
    val fields: Any? = null
)

data class PayloadItem(
    val key: Any? = null,
    val value: Any? = null
)

data class Fields(
    val successUrl: Any? = null,
    val errorUrl: Any? = null,
    val ttl: Any? = null
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
            phone = "79000000000",
            email = "klepa_e@mail.ru",
            description = "TestDeadCow",
            fields = Fields(
                successUrl = "https://uat-pay.av.ru/success/123456",
                errorUrl = "https://uat-pay.av.ru//error/123456",
                ttl = 3600
            )
        )
    }

    fun noAmountRequest(): SberGateBodyRequest {
        return SberGateBodyRequest(
            invoice_id = generateRandom10Digit().toString(),
            mobile = false,
            platform = "WEB",
            account_id = "1-2Q4PVM7Z",
            name = "randomName",
            payload = listOf(PayloadItem("testKey", "testValue")),
            phone = "79000000000",
            email = "klepa_e@mail.ru",
            description = "TestDeadCow",
            fields = Fields(
                successUrl = "https://uat-pay.av.ru/success/123456",
                errorUrl = "https://uat-pay.av.ru//error/123456",
                ttl = 3600
            )
        )
    }

    fun noMobileRequest(): SberGateBodyRequest {
        return SberGateBodyRequest(
            amount = "2",
            invoice_id = generateRandom10Digit().toString(),
            platform = "WEB",
            account_id = "1-2Q4PVM7Z",
            //secure_pay = null,
            name = "randomName",
            payload = listOf(PayloadItem("testKey", "testValue")),
            phone = "79000000000",
            email = "klepa_e@mail.ru",
            description = "TestDeadCow",
            fields = Fields(
                successUrl = "https://uat-pay.av.ru/success/123456",
                errorUrl = "https://uat-pay.av.ru//error/123456",
                ttl = 3600
            )
        )
    }

    fun noPlatformRequest(): SberGateBodyRequest {
        return SberGateBodyRequest(
            amount = "2",
            invoice_id = generateRandom10Digit().toString(),
            mobile = false,
            account_id = "1-2Q4PVM7Z",
            //secure_pay = null,
            name = "randomName",
            payload = listOf(PayloadItem("testKey", "testValue")),
            phone = "79000000000",
            email = "klepa_e@mail.ru",
            description = "TestDeadCow",
            fields = Fields(
                successUrl = "https://uat-pay.av.ru/success/123456",
                errorUrl = "https://uat-pay.av.ru//error/123456",
                ttl = 3600
            )
        )
    }

    fun noInvoiceIdRequest(): SberGateBodyRequest {
        return SberGateBodyRequest(
            amount = "2",
            mobile = false,
            platform = "WEB",
            account_id = "1-2Q4PVM7Z",
            //secure_pay = null,
            name = "randomName",
            payload = listOf(PayloadItem("testKey", "testValue")),
            phone = "79000000000",
            email = "klepa_e@mail.ru",
            description = "TestDeadCow",
            fields = Fields(
                successUrl = "https://uat-pay.av.ru/success/123456",
                errorUrl = "https://uat-pay.av.ru//error/123456",
                ttl = 3600
            )
        )
    }

    // Формирует URL с query-параметром method_uuid
    fun buildUrlWithMethodUuid(basePath: String, methodUuid: String): String {
        return "$basePath?method_uuid=$methodUuid"
    }
}

//Класс-конструктор тела запроса с возможностью отправить null в полях
data class SberGateBodyNullFieldsRequest(
    val amount: Any? = null,
    val invoice_id: Any? = null,
    val mobile: Any? = null,
    val platform: Any? = null,
    val account_id: Any? = null,
    //val secure_pay: String? = null,
    val name: Any? = null,
    val payload: Any? = null,
    val phone: Any? = null,
    val email: Any? = null,
    val description: Any? = null,
    val fields: Any? = null
)

data class NullPayloadItem(
    val key: Any? = null,
    val value: Any? = null
)

data class NullFields(
    val successUrl: Any? = null,
    val errorUrl: Any? = null,
    val ttl: Any? = null
)

// Вспомогательный объект для генерации тестовых данных
object SberGateNullFieldsRequestGenerator {

    private val random = Random

    // Генерирует случайное 10‑значное число
    fun generateRandom10Digit(): Long {
        return (1e9.toLong()..9999999999L).random(random)
    }

    // Базовый шаблон запроса (можно расширять)
    fun baseRequest(): SberGateBodyNullFieldsRequest {
        return SberGateBodyNullFieldsRequest(
            amount = "2",
            invoice_id = generateRandom10Digit().toString(),
            mobile = false,
            platform = "WEB",
            account_id = "1-2Q4PVM7Z",
            //secure_pay = null,
            name = "randomName",
            payload = listOf(NullPayloadItem("testKey", "testValue")),
            phone = "79000000000",
            email = "klepa_e@mail.ru",
            description = "TestDeadCow",
            fields = NullFields(
                successUrl = "https://uat-pay.av.ru/success/123456",
                errorUrl = "https://uat-pay.av.ru//error/123456",
                ttl = 3600
            )
        )
    }

    fun noAmountRequest(): SberGateBodyNullFieldsRequest {
        return SberGateBodyNullFieldsRequest(
            invoice_id = generateRandom10Digit().toString(),
            mobile = false,
            platform = "WEB",
            account_id = "1-2Q4PVM7Z",
            name = "randomName",
            payload = listOf(PayloadItem("testKey", "testValue")),
            phone = "79000000000",
            email = "klepa_e@mail.ru",
            description = "TestDeadCow",
            fields = Fields(
                successUrl = "https://uat-pay.av.ru/success/123456",
                errorUrl = "https://uat-pay.av.ru//error/123456",
                ttl = 3600
            )
        )
    }

    fun noMobileRequest(): SberGateBodyNullFieldsRequest {
        return SberGateBodyNullFieldsRequest(
            amount = "2",
            invoice_id = generateRandom10Digit().toString(),
            platform = "WEB",
            account_id = "1-2Q4PVM7Z",
            //secure_pay = null,
            name = "randomName",
            payload = listOf(PayloadItem("testKey", "testValue")),
            phone = "79000000000",
            email = "klepa_e@mail.ru",
            description = "TestDeadCow",
            fields = Fields(
                successUrl = "https://uat-pay.av.ru/success/123456",
                errorUrl = "https://uat-pay.av.ru//error/123456",
                ttl = 3600
            )
        )
    }

    fun noPlatformRequest(): SberGateBodyNullFieldsRequest {
        return SberGateBodyNullFieldsRequest(
            amount = "2",
            invoice_id = generateRandom10Digit().toString(),
            mobile = false,
            account_id = "1-2Q4PVM7Z",
            //secure_pay = null,
            name = "randomName",
            payload = listOf(PayloadItem("testKey", "testValue")),
            phone = "79000000000",
            email = "klepa_e@mail.ru",
            description = "TestDeadCow",
            fields = Fields(
                successUrl = "https://uat-pay.av.ru/success/123456",
                errorUrl = "https://uat-pay.av.ru//error/123456",
                ttl = 3600
            )
        )
    }

    fun noInvoiceIdRequest(): SberGateBodyNullFieldsRequest {
        return SberGateBodyNullFieldsRequest(
            amount = "2",
            mobile = false,
            platform = "WEB",
            account_id = "1-2Q4PVM7Z",
            //secure_pay = null,
            name = "randomName",
            payload = listOf(PayloadItem("testKey", "testValue")),
            phone = "79000000000",
            email = "klepa_e@mail.ru",
            description = "TestDeadCow",
            fields = Fields(
                successUrl = "https://uat-pay.av.ru/success/123456",
                errorUrl = "https://uat-pay.av.ru//error/123456",
                ttl = 3600
            )
        )
    }

    // Формирует URL с query-параметром method_uuid
    fun buildUrlWithMethodUuid(basePath: String, methodUuid: String): String {
        return "$basePath?method_uuid=$methodUuid"
    }
}
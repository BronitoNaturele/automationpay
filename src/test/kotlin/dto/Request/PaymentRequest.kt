//Модели данных (DTO)
//RequestDTO
//Описывают структуру тел запросов

package dto.Request

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * DTO для запроса на получение платёжных методов.
 * Пример использования:
 * ```
 * val request = PaymentRequest(userId = "user-123")
 * apiClient.post("/api/v1/payment/methods", request)
 * ```
 */
data class PaymentRequest(
    @JsonProperty("user_id")
    val userId: String,

    @JsonProperty("amount")
    val amount: Double? = null,

    @JsonProperty("currency")
    val currency: String = "RUB"
)
//Модели данных (DTO)
//RequestDTO
//Описывают структуру тел запросов

package dto.Request

//DTO для тела запроса к API (если потребуется в будущем).
//Сейчас не используется в GET‑запросе, но оставлен для расширения. */
data class PaymentRequest(
    val someField: String? = null
)
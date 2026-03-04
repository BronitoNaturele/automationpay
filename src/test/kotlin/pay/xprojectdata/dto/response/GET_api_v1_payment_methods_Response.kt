// Модели данных (DTO). RequestDTO. Описывают структуру ответов

package pay.xprojectdata.dto.response

data class BodyPaymentMethodsResponse(
    val data: List<PaymentMethod>  // 1. массив элементов
)

// 2. Элемент массива (каждый объект внутри data)
data class PaymentMethod(
    val name: String,
    val uuid: String,
    val type_id: Int,
    val weight: Int
)

// Тело ответа для 401
data class noTokenBody(
    val error_message: String,
    val error_code: String,
    val type_error: String
)
//Модели данных (DTO)
//RequestDTO
//Описывают структуру тел запросов

package dto.Request

import dto.Response.PaymentMethod


data class BodyPaymentMethodsResponse(
    val data: List<PaymentMethod>  // массив элементов
)

// 2. Элемент массива (каждый объект внутри data)
data class PaymentMethod(
    val name: String,
    val uuid: String,
    val type_id: Int,
    val weight: Int
)
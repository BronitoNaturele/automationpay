//Модели данных (DTO)
//RequestDTO
//Описывают структуру ответов

package dto.Response

import com.fasterxml.jackson.annotation.JsonProperty

//Корневой DTO для ответа API.
//@param data Список доступных платёжных методов.
data class PaymentResponse(
    @JsonProperty("data")
    val data: List<PaymentMethod>
)


//DTO для отдельного платёжного метода.
 data class PaymentMethod(
    val name: String,
    val uuid: String,
    @JsonProperty("type_id")
    val typeId: Int,
    val weight: Int
)
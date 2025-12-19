//Модели данных (DTO)
//RequestDTO
//Описывают структуру тел запросов
package dto.Request

data class UserRequest(
    val name: String,
    val email: String
)
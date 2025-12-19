//Модели данных (DTO)
//ResponseDTO
//Описывают структуру тел ответов API.

package dto.Response

data class UserResponse(
    val id: Int,
    val name: String,
    val email: String
)
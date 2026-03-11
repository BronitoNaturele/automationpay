package pay.xprojectdata.dto.request

// Тело ответа для ошибки: 406
data class errorPayMethodUuidResponseBodyGlobal(
    val error_message: String,
    val error_code: String,
    val type_error: String
)

// Тело ответа для ошибки: 405
data class errorNoMethodPayMethodUuidResponseBodyGlobal(
    val error_message: String,
    val error_code: String,
    val type_error: String,
    val error_description: String
)
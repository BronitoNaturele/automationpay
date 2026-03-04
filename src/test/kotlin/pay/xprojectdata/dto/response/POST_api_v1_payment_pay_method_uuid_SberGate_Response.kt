package pay.xprojectdata.dto.response

// Тело ответа для ошибок: 401 и 406
data class errorPayMethodUuidResponseBodySberGate(
    val error_message: String,
    val error_code: String,
    val type_error: String
)

// Тело ответа для ошибки: 405
data class errorNoMethodPayMethodUuidResponseBodySberGate(
    val error_message: String,
    val error_code: String,
    val type_error: String,
    val error_description: String
)
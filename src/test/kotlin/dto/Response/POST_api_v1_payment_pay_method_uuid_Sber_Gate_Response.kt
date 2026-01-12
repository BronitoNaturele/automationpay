package dto.Response

data class SberGateResponseBody(
    val success: Boolean,
    val id: Long,
    val url: String,
    val additional_fields: List<Any>
)
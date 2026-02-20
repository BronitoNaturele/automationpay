package pay.xprojectdata.dto.response

data class sberGateResponseBody(
    val success: Boolean,
    val id: Long,
    val url: String,
    val additional_fields: List<Any>
)
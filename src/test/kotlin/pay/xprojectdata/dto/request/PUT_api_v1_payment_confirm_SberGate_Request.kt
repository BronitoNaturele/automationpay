package pay.xprojectdata.dto.request

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class sberGateBodyRequestForConfirmPutRequest(
    val id: Int? = null,
    val amount: Any? = null,
    )

object sberGateBodyRequestForConfirmPutRequestGenerator {
    fun baseRequest(id: Int? = null): sberGateBodyRequestForConfirmPutRequest {
        return sberGateBodyRequestForConfirmPutRequest(
            amount = "2",
            id = id
        )
    }
}


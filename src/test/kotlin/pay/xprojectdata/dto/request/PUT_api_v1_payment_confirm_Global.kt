package pay.xprojectdata.dto.request

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class globalBodyRequestForConfirmPutRequest(
    val id: Any? = null,
    val amount: Any? = null,
)

object globalBodyRequestForConfirmPutRequestGenerator {
    fun baseRequest(id: Any? = null): globalBodyRequestForConfirmPutRequest {
        return globalBodyRequestForConfirmPutRequest(
            amount = 2,
            id = id
        )
    }

    fun noAmountRequest(id: Any? = null): globalBodyRequestForConfirmPutRequest {
        return globalBodyRequestForConfirmPutRequest(
            id = id
        )
    }

    fun noIdRequest(id: Any? = null): globalBodyRequestForConfirmPutRequest {
        return globalBodyRequestForConfirmPutRequest(
            amount = 2
        )
    }
}


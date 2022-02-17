package ru.deepthreads.rest.models.response.errors

import ru.deepthreads.rest.Constants
import ru.deepthreads.rest.models.DebugFields
import ru.deepthreads.rest.models.response.BaseAPIResponse

data class AccessDeniedResponse(
    override val apiStatusCode: Int = Constants.STATUSCODE.ACCESS_DENIED,
    override val apiMessage: String = "Access Denied.",
    override val debugFields: DebugFields
): BaseAPIResponse

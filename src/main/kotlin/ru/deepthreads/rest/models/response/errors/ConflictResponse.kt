package ru.deepthreads.rest.models.response.errors

import ru.deepthreads.rest.Constants
import ru.deepthreads.rest.models.other.DebugFields
import ru.deepthreads.rest.models.response.BaseAPIResponse

data class ConflictResponse(
    override val apiStatusCode: Int = Constants.STATUSCODE.CONFLICT,
    override val apiMessage: String = "It seems that you have already performed this action",
    override val debugFields: DebugFields
): BaseAPIResponse

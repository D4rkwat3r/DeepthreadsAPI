package ru.deepthreads.rest.models.response.errors

import ru.deepthreads.rest.Constants
import ru.deepthreads.rest.models.other.DebugFields
import ru.deepthreads.rest.models.response.BaseAPIResponse

data class YouAreBlockedResponse(
    override val apiStatusCode: Int = Constants.STATUSCODE.YOU_ARE_BLOCKED_BY_USER,
    override val apiMessage: String,
    override val debugFields: DebugFields
): BaseAPIResponse

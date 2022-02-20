package ru.deepthreads.rest.models.response.errors

import ru.deepthreads.rest.Constants
import ru.deepthreads.rest.models.other.DebugFields
import ru.deepthreads.rest.models.response.BaseAPIResponse

data class TakenDeepIDResponse(
    override val apiStatusCode: Int = Constants.STATUSCODE.TAKEN_DEEP_ID,
    override val apiMessage: String = "The Deep ID is already taken by another user. You can try another one.",
    override val debugFields: DebugFields
): BaseAPIResponse

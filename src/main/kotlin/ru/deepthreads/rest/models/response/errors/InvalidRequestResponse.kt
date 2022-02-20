package ru.deepthreads.rest.models.response.errors

import ru.deepthreads.rest.Constants
import ru.deepthreads.rest.models.other.DebugFields
import ru.deepthreads.rest.models.response.BaseAPIResponse

data class InvalidRequestResponse(
    override val apiStatusCode: Int = Constants.STATUSCODE.INVALID_REQUEST,
    override val apiMessage: String = "Invalid request! Your app has sent something inarticulate. Please update it to the latest version.",
    override val debugFields: DebugFields
): BaseAPIResponse
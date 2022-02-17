package ru.deepthreads.rest.models.response.errors

import ru.deepthreads.rest.Constants
import ru.deepthreads.rest.models.DebugFields
import ru.deepthreads.rest.models.response.BaseAPIResponse

data class UnauthorizedResponse(
    override val apiStatusCode: Int = Constants.STATUSCODE.UNAUTHORIZED,
    override val apiMessage: String = "You are not logged in, or the token has expired.",
    override val debugFields: DebugFields
): BaseAPIResponse

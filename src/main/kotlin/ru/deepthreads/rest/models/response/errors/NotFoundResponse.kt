package ru.deepthreads.rest.models.response.errors

import ru.deepthreads.rest.Constants
import ru.deepthreads.rest.models.DebugFields
import ru.deepthreads.rest.models.response.BaseAPIResponse

data class NotFoundResponse(
    override val apiStatusCode: Int = Constants.STATUSCODE.NOT_FOUND,
    override val apiMessage: String = "Oops! What you requested doesn't seem to exist.",
    override val debugFields: DebugFields
): BaseAPIResponse
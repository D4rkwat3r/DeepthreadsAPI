package ru.deepthreads.rest.models.response.success

import ru.deepthreads.rest.Constants
import ru.deepthreads.rest.models.DebugFields
import ru.deepthreads.rest.models.response.BaseAPIResponse

data class EmptyOKResponse(
    override val apiStatusCode: Int = Constants.STATUSCODE.NORMAL,
    override val apiMessage: String = "OK",
    override val debugFields: DebugFields? = null
): BaseAPIResponse
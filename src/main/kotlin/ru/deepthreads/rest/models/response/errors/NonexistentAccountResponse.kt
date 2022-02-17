package ru.deepthreads.rest.models.response.errors

import ru.deepthreads.rest.Constants
import ru.deepthreads.rest.models.DebugFields
import ru.deepthreads.rest.models.response.BaseAPIResponse

data class NonexistentAccountResponse(
    override val apiStatusCode: Int = Constants.STATUSCODE.NONEXISTENT_ACCOUNT,
    override val apiMessage: String = "The account does not exist. If you haven't registered yet, do it.",
    override val debugFields: DebugFields
): BaseAPIResponse

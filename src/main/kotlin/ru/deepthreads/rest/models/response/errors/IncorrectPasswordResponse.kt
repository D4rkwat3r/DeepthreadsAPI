package ru.deepthreads.rest.models.response.errors

import ru.deepthreads.rest.Constants
import ru.deepthreads.rest.models.other.DebugFields
import ru.deepthreads.rest.models.response.BaseAPIResponse

data class IncorrectPasswordResponse(
    override val apiStatusCode: Int = Constants.STATUSCODE.INCORRECT_PASSWORD,
    override val apiMessage: String = "It looks like the password is incorrect.",
    override val debugFields: DebugFields
): BaseAPIResponse

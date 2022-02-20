package ru.deepthreads.rest.models.response.errors

import ru.deepthreads.rest.Constants
import ru.deepthreads.rest.models.other.DebugFields
import ru.deepthreads.rest.models.other.GoogleAuthException
import ru.deepthreads.rest.models.response.BaseAPIResponse

data class GoogleAuthFailResponse(
    override val apiStatusCode: Int = Constants.STATUSCODE.GOOGLE_RETURNED_ERROR,
    override val apiMessage: String = "Google auth failed.",
    override val debugFields: DebugFields,
    val googleAuthException: GoogleAuthException
): BaseAPIResponse
package ru.deepthreads.rest.models.response.errors

import ru.deepthreads.rest.Constants
import ru.deepthreads.rest.models.DebugFields
import ru.deepthreads.rest.models.response.BaseAPIResponse

data class AlreadyMember(
    override val apiStatusCode: Int = Constants.STATUSCODE.ALREADY_A_MEMBER,
    override val apiMessage: String = "Already a member of this chat",
    override val debugFields: DebugFields
): BaseAPIResponse

package ru.deepthreads.rest.models.response.errors

import ru.deepthreads.rest.Constants
import ru.deepthreads.rest.models.DebugFields
import ru.deepthreads.rest.models.response.BaseAPIResponse

data class NotMember(
    override val apiStatusCode: Int = Constants.STATUSCODE.NOT_A_MEMBER,
    override val apiMessage: String = "You are not a member of this chat",
    override val debugFields: DebugFields
): BaseAPIResponse
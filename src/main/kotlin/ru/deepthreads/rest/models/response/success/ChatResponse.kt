package ru.deepthreads.rest.models.response.success

import ru.deepthreads.rest.Constants
import ru.deepthreads.rest.models.other.DebugFields
import ru.deepthreads.rest.models.views.ChatView
import ru.deepthreads.rest.models.response.BaseAPIResponse

data class ChatResponse(
    override val apiStatusCode: Int = Constants.STATUSCODE.NORMAL,
    override val apiMessage: String = "OK",
    override val debugFields: DebugFields? = null,
    val chat: ChatView
): BaseAPIResponse
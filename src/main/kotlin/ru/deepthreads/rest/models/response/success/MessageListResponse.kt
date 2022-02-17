package ru.deepthreads.rest.models.response.success

import ru.deepthreads.rest.Constants
import ru.deepthreads.rest.models.DebugFields
import ru.deepthreads.rest.models.response.BaseAPIResponse
import ru.deepthreads.rest.models.views.MessageView

data class MessageListResponse(
    override val apiStatusCode: Int = Constants.STATUSCODE.NORMAL,
    override val apiMessage: String = "OK",
    override val debugFields: DebugFields? = null,
    val messageList: List<MessageView>
): BaseAPIResponse
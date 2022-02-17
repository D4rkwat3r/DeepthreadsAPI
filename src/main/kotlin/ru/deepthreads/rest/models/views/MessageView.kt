package ru.deepthreads.rest.models.views

import ru.deepthreads.rest.models.entity.BaseAPIEntity
import ru.deepthreads.rest.models.entity.UserProfile

data class MessageView(
    override val objectId: String,
    override val status: Int,
    override val createdTime: Long,
    val content: String?,
    val type: Int,
    val sender: UserProfile,
    val replyMessage: MessageView?
): BaseAPIEntity

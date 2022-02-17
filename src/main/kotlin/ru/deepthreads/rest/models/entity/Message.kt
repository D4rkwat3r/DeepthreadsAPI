package ru.deepthreads.rest.models.entity

import ru.deepthreads.rest.Constants

data class Message(
    override val objectId: String,
    override val createdTime: Long,
    override val status: Int = Constants.OBJECTSTATUS.NORMAL,
    val content: String?,
    val type: Int,
    val senderUid: String,
    val replyMessage: Message?
): BaseAPIEntity

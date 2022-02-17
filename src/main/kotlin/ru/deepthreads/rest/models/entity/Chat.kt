package ru.deepthreads.rest.models.entity

import ru.deepthreads.rest.Constants

data class Chat(
    override val objectId: String,
    override val createdTime: Long,
    override val status: Int = Constants.OBJECTSTATUS.NORMAL,
    val title: String?,
    val iconUrl: String?,
    val backgroundUrl: String?,
    val type: Int,
    val membersUidList: List<String>,
    val ownerUid: String,
    val messages: List<Message>
): BaseAPIEntity
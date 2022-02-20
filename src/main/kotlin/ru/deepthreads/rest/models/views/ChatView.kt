package ru.deepthreads.rest.models.views

import ru.deepthreads.rest.models.entity.BaseAPIEntity

class ChatView(
    override val objectId: String,
    override val status: Int,
    override val createdTime: Long,
    val title: String?,
    val iconUrl: String?,
    val backgroundUrl: String?,
    val type: Int,
    val owner: UserProfileView,
    val lastMessage: MessageView?,
    val membersCount: Int,
    val meInChat: Boolean
): BaseAPIEntity

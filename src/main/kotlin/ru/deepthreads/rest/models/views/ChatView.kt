package ru.deepthreads.rest.models.views

import ru.deepthreads.rest.models.entity.BaseAPIEntity
import ru.deepthreads.rest.models.entity.UserProfile

class ChatView(
    override val objectId: String,
    override val status: Int,
    override val createdTime: Long,
    val title: String?,
    val iconUrl: String?,
    val backgroundUrl: String?,
    val type: Int,
    val owner: UserProfile,
    val lastMessage: MessageView?,
    val membersCount: Int,
    val meInChat: Boolean
): BaseAPIEntity

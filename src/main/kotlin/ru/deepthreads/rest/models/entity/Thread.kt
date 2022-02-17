package ru.deepthreads.rest.models.entity

import ru.deepthreads.rest.Constants

data class Thread(
    override val objectId: String,
    override val createdTime: Long,
    override val status: Int = Constants.OBJECTSTATUS.NORMAL,
    val name: String,
    val themeColor: String,
    val iconUrl: String,
    val coverUrl: String,
    val backgroundUrl: String,
    val membersUidList: List<String>,
    val threadModerationUidList: List<String>,
    val threadAdminUid: String,
    val posts: List<Post>,
    val chats: List<Chat>
): BaseAPIEntity

package ru.deepthreads.rest.models.views

import ru.deepthreads.rest.Constants
import ru.deepthreads.rest.models.entity.BaseAPIEntity

data class UserProfileView(
    override val objectId: String,
    override val createdTime: Long,
    override val status: Int = Constants.OBJECTSTATUS.NORMAL,
    val nickname: String,
    val deepId: String,
    val pictureUrl: String,
    val subscribersCount: Int,
    val commentsCount: Int,
    val role: Int = Constants.ROLE.USER
): BaseAPIEntity
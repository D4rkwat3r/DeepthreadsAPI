package ru.deepthreads.rest.models.views

import ru.deepthreads.rest.models.entity.BaseAPIEntity
import ru.deepthreads.rest.models.entity.UserProfile

data class PostView(
    override val objectId: String,
    override val status: Int,
    override val createdTime: Long,
    val author: UserProfile,
    val title: String,
    val content: String,
    val coverUrl: String,
    val backgroundUrl: String,
    val likeCount: Int,
    val commentCount: Int,
    val isLikedByMe: Boolean
): BaseAPIEntity

package ru.deepthreads.rest.models.views

import ru.deepthreads.rest.models.entity.BaseAPIEntity

data class PostView(
    override val objectId: String,
    override val status: Int,
    override val createdTime: Long,
    val author: UserProfileView,
    val title: String,
    val content: String,
    val coverUrl: String,
    val backgroundUrl: String,
    val likesCount: Int,
    val commentsCount: Int,
    val isLikedByMe: Boolean
): BaseAPIEntity

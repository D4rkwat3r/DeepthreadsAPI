package ru.deepthreads.rest.models.views

import ru.deepthreads.rest.models.entity.BaseAPIEntity

data class CommentView(
    override val objectId: String,
    override val status: Int,
    override val createdTime: Long,
    val author: UserProfileView,
    val content: String,
    val likesCount: Int,
    val isLikedByMe: Boolean
): BaseAPIEntity

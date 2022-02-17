package ru.deepthreads.rest.models.views

import ru.deepthreads.rest.models.entity.BaseAPIEntity
import ru.deepthreads.rest.models.entity.UserProfile

data class CommentView(
    override val objectId: String,
    override val status: Int,
    override val createdTime: Long,
    val author: UserProfile,
    val content: String,
    val likeCount: Int,
    val isLikedByMe: Boolean
): BaseAPIEntity

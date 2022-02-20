package ru.deepthreads.rest.models.views

import ru.deepthreads.rest.models.entity.BaseAPIEntity

data class LikeView(
    override val objectId: String,
    override val status: Int,
    override val createdTime: Long,
    val author: UserProfileView
): BaseAPIEntity

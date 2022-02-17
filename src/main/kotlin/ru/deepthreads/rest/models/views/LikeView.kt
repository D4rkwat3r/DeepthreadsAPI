package ru.deepthreads.rest.models.views

import ru.deepthreads.rest.models.entity.BaseAPIEntity
import ru.deepthreads.rest.models.entity.UserProfile

data class LikeView(
    override val objectId: String,
    override val status: Int,
    override val createdTime: Long,
    val author: UserProfile
): BaseAPIEntity

package ru.deepthreads.rest.models.views

import ru.deepthreads.rest.models.entity.BaseAPIEntity

data class AccountView(
    override val objectId: String,
    override val createdTime: Long,
    override val status: Int,
    val nickname: String,
    val deepId: String,
    val password: String?,
    val authToken: String,
    val userProfile: UserProfileView
): BaseAPIEntity

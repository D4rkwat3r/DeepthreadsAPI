package ru.deepthreads.rest.models.entity

data class Account(
    override val objectId: String,
    override val createdTime: Long,
    override val status: Int,
    val nickname: String,
    val deepId: String,
    val password: String?,
    val authToken: String,
    val userProfile: UserProfile
): BaseAPIEntity

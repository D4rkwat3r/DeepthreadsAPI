package ru.deepthreads.rest.models

import ru.deepthreads.rest.models.entity.UserProfile

data class Account(
    val nickname: String,
    val deepId: String,
    val password: String,
    val authToken: String,
    val createdTime: Long,
    val userProfile: UserProfile
)

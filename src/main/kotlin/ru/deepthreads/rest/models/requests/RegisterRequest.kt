package ru.deepthreads.rest.models.requests

data class RegisterRequest(
    val nickname: String,
    val deepId: String,
    val password: String,
    val pictureResource: String
)

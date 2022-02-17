package ru.deepthreads.rest.models.requests


data class LoginRequest(
    val deepId: String,
    val password: String,
)

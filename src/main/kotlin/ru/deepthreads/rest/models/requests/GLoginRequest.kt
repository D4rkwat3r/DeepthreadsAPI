package ru.deepthreads.rest.models.requests

data class GLoginRequest(
    val gToken: String,
    val authTimeMillisecond: Long
)

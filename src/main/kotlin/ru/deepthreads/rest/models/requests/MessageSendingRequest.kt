package ru.deepthreads.rest.models.requests

data class MessageSendingRequest(
    val content: String,
    val type: Int
)

package ru.deepthreads.rest.models.requests

data class PublicChatCreatingRequest(
    val title: String,
    val iconResource: String,
    val backgroundResource: String
)

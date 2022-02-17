package ru.deepthreads.rest.models.requests

data class PostCreatingRequest(
    val title: String,
    val content: String,
    val coverResource: String,
    val backgroundResource: String
)
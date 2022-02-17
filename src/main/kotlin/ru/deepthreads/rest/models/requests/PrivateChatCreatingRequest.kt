package ru.deepthreads.rest.models.requests

data class PrivateChatCreatingRequest(
    val invitedUsersIds: List<String>,
    val initialMessage: String
)

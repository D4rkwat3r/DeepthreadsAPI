package ru.deepthreads.rest.models.websockets

data class WebSocketAction(
    val actionType: Int,
    val payload: String?
)

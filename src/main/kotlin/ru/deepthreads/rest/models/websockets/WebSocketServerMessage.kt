package ru.deepthreads.rest.models.websockets

data class WebSocketServerMessage(
    val eventType: Int,
    val payload: Any?,
    val eventSource: String?
)

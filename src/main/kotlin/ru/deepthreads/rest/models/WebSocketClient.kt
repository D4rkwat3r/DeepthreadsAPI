package ru.deepthreads.rest.models

data class WebSocketClient(
    val account: Account,
    var lastPingTime: Long
)

package ru.deepthreads.rest.models.other

import ru.deepthreads.rest.models.views.AccountView

data class WebSocketClient(
    val account: AccountView,
    var lastPingTime: Long
)

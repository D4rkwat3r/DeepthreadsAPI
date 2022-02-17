package ru.deepthreads.rest.websockets

import ru.deepthreads.rest.models.views.MessageView
import ru.deepthreads.rest.models.websockets.WebSocketServerMessage
import ru.deepthreads.rest.services.ChatService

object ChatWebSocket : DeepThreadsWebSocket() {
    fun broadcastNewMessage(message: MessageView, chatId: String) {
        sessions.forEach { session ->
            if (ChatService.checkUserInChat(chatId, session.value.account.userProfile.objectId))
                send(session.key, WebSocketServerMessage(2, message, chatId))
        }
    }
}

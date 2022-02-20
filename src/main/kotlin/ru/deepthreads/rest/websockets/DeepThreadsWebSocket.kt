package ru.deepthreads.rest.websockets

import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import ru.deepthreads.rest.mapper
import ru.deepthreads.rest.models.other.WebSocketClient
import ru.deepthreads.rest.models.websockets.WebSocketAction
import ru.deepthreads.rest.models.websockets.WebSocketServerMessage
import ru.deepthreads.rest.services.AccountService
import java.time.Instant
import java.util.*

open class DeepThreadsWebSocket : TextWebSocketHandler() {

    private val timer = Timer()
    val sessions = mutableMapOf<WebSocketSession, WebSocketClient>()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val account = AccountService.getByToken(
            session.handshakeHeaders["AuthToken"]!![0]
        )
        sessions[session] = WebSocketClient(account, Instant.now().epochSecond)
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        try {
            val action = mapper.readValue(message.payload, WebSocketAction::class.java)
            if (action.actionType == 1) {
                sessions[session]?.lastPingTime = Instant.now().epochSecond
            }
        } catch (e: Exception) {
            println("WebSocket exception: ${e::class.java.name}")
        }
        super.handleTextMessage(session, message)
    }

    private fun startPingResolver() {
        timer.schedule(object: TimerTask() {
            override fun run() {
                resolve()
            }
        }, 15)
    }

    private fun resolve() {
        val badSessions = mutableListOf<WebSocketSession>()
        sessions.forEach { part ->
            if (Instant.now().epochSecond - part.value.lastPingTime > 15) {
                badSessions += part.key
            }
        }
        badSessions.forEach { session ->
            send(session, WebSocketServerMessage(1, null, null))
            if (session.isOpen)
                session.close()
            sessions.remove(session)
        }
        startPingResolver()
    }

    fun send(session: WebSocketSession, content: Any) {
        if (!session.isOpen) {
            sessions.remove(session)
            return
        }
        session.sendMessage(
            TextMessage(
                mapper.writeValueAsString(content)
            )
        )
    }

    init {
        startPingResolver()
    }

}
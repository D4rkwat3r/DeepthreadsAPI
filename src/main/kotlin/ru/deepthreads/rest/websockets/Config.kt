package ru.deepthreads.rest.websockets

import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import ru.deepthreads.rest.interceptors.WSInterceptor

@Configuration @EnableWebSocket
class Config : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(ChatWebSocket, "/ws/v1/chat")
            .addInterceptors(WSInterceptor())
    }
}
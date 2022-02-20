package ru.deepthreads.rest.interceptors

import org.springframework.http.HttpStatus
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.HandshakeInterceptor
import ru.deepthreads.rest.exceptions.auth.Unauthorized
import ru.deepthreads.rest.mapper
import ru.deepthreads.rest.models.other.DebugFields
import ru.deepthreads.rest.models.response.errors.UnauthorizedResponse
import ru.deepthreads.rest.services.AccountService
import java.lang.Exception
import java.lang.NullPointerException

class WSInterceptor : HandshakeInterceptor {
    override fun beforeHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String, Any>
    ): Boolean {
        try {
            AccountService.validateToken(request.headers["AuthToken"]!![0])
        } catch (e: Unauthorized) {
            response.body.write(mapper.writeValueAsString(UnauthorizedResponse(
                debugFields = DebugFields(
                    e::class.java.name,
                    e.message
                )
            )).toByteArray())
            response.setStatusCode(HttpStatus.FORBIDDEN)
            return false
        } catch (e: NullPointerException) {
            response.body.write(mapper.writeValueAsString(UnauthorizedResponse(
                debugFields = DebugFields(
                    e::class.java.name,
                    e.message
                )
            )).toByteArray())
            response.setStatusCode(HttpStatus.UNAUTHORIZED)
            return false
        }
        return true
    }
    override fun afterHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        exception: Exception?
    ) {
        println("WS Connection with ${request.remoteAddress} established")
    }

}
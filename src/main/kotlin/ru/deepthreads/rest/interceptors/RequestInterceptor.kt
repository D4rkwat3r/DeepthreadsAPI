package ru.deepthreads.rest.interceptors

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.servlet.HandlerInterceptor
import ru.deepthreads.rest.dts.DTSValidator
import ru.deepthreads.rest.exceptions.auth.Unauthorized
import ru.deepthreads.rest.exceptions.other.InvalidRequest
import ru.deepthreads.rest.services.AccountService

class RequestInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (request.requestURI.startsWith("/api")) {
            verifyRequestSignatures(request)
        }
        if (!request.requestURI.contains("/auth/")
            && !request.requestURI.contains("/upload-media")
            && !request.requestURI.contains("/static/")
            && !request.requestURI.contains("/dynamic/")
            && !request.requestURI.contains("/event-log/")
            || request.requestURI.contains("/users/me")
            || request.requestURI.contains("/me/")
        ) {
            verifyRequestAuthInfo(request)
        }
        println("[${this::class.java.name}] ${request.method} -> ${request.requestURI}")
        return true
    }

    private fun verifyRequestAuthInfo(request: HttpServletRequest): Boolean {
        val headersNamesList = request.headerNames.toList()
        if (!headersNamesList.contains("authtoken")) {
            throw Unauthorized()
        } else {
            AccountService.validateToken(request.getHeader("authtoken"))
        }
        return true
    }

    private fun verifyRequestSignatures(request: HttpServletRequest): Boolean {
        val headersNamesList = request.headerNames.toList()
        if (!headersNamesList.contains("dtscontext")) {
            throw InvalidRequest("Missing DTSContext header")
        } else if (!headersNamesList.contains("dtsnonce")) {
            throw InvalidRequest("Missing DTSNonce header")
        } else {
            if (
                request.getHeader("dtscontext") != "debug"
                && !DTSValidator.verifyContext(
                    request.getHeader("dtscontext"),
                    request.getHeader("dtsnonce"),
                    request.requestURI
                )
            ) {
                throw InvalidRequest("Incorrect DTSContext signature header")
            }
        }
        return true
    }
}
package ru.deepthreads.rest.handlers

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import ru.deepthreads.rest.exceptions.auth.IncorrectPassword
import ru.deepthreads.rest.exceptions.auth.NonexistentAccount
import ru.deepthreads.rest.exceptions.auth.TakenDeepID
import ru.deepthreads.rest.exceptions.auth.Unauthorized
import ru.deepthreads.rest.models.DebugFields
import ru.deepthreads.rest.models.response.errors.IncorrectPasswordResponse
import ru.deepthreads.rest.models.response.errors.NonexistentAccountResponse
import ru.deepthreads.rest.models.response.errors.TakenDeepIDResponse
import ru.deepthreads.rest.models.response.errors.UnauthorizedResponse

@ControllerAdvice
class AuthHandlers : ResponseEntityExceptionHandler() {
    @ExceptionHandler(IncorrectPassword::class)
    fun handle(exception: IncorrectPassword, request: WebRequest): ResponseEntity<IncorrectPasswordResponse> {
        val body = IncorrectPasswordResponse(
            debugFields = DebugFields(
                exception::class.java.name,
                null
            )
        )
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .contentType(MediaType.APPLICATION_JSON)
            .body(body)
    }
    @ExceptionHandler(NonexistentAccount::class)
    fun handle(exception: NonexistentAccount, request: WebRequest): ResponseEntity<NonexistentAccountResponse> {
        val body = NonexistentAccountResponse(
            debugFields = DebugFields(
                exception::class.java.name,
                exception.message
            )
        )
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .contentType(MediaType.APPLICATION_JSON)
            .body(body)
    }
    @ExceptionHandler(TakenDeepID::class)
    fun handle(exception: TakenDeepID, request: WebRequest): ResponseEntity<TakenDeepIDResponse> {
        val body = TakenDeepIDResponse(
            debugFields = DebugFields(
                exception::class.java.name,
                exception.message
            )
        )
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .contentType(MediaType.APPLICATION_JSON)
            .body(body)
    }
    @ExceptionHandler(Unauthorized::class)
    fun handle(exception: Unauthorized, request: WebRequest): ResponseEntity<UnauthorizedResponse> {
        val body = UnauthorizedResponse(
            debugFields = DebugFields(
                exception::class.java.name,
                exception.message
            )
        )
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(body)
    }
}
package ru.deepthreads.rest.handlers

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import ru.deepthreads.rest.exceptions.chat.AlreadyAMember
import ru.deepthreads.rest.exceptions.chat.NotAMember
import ru.deepthreads.rest.exceptions.chat.YouAreBlocked
import ru.deepthreads.rest.models.other.DebugFields
import ru.deepthreads.rest.models.response.errors.AlreadyMemberResponse
import ru.deepthreads.rest.models.response.errors.NotMemberResponse
import ru.deepthreads.rest.models.response.errors.YouAreBlockedResponse

@ControllerAdvice
class ChatHandlers {
    @ExceptionHandler(AlreadyAMember::class)
    fun handle(exception: AlreadyAMember, request: WebRequest): ResponseEntity<AlreadyMemberResponse> {
        val body = AlreadyMemberResponse(
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
    @ExceptionHandler(NotAMember::class)
    fun handle(exception: NotAMember, request: WebRequest): ResponseEntity<NotMemberResponse> {
        val body = NotMemberResponse(
            debugFields = DebugFields(
                exception::class.java.name,
                exception.message
            )
        )
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .contentType(MediaType.APPLICATION_JSON)
            .body(body)
    }
    @ExceptionHandler(YouAreBlocked::class)
    fun handle(exception: YouAreBlocked, request: WebRequest): ResponseEntity<YouAreBlockedResponse> {
        val body = YouAreBlockedResponse(
            apiMessage = "You are blocked by ${exception.userNickname}",
            debugFields = DebugFields(
                exception::class.java.name,
                exception.message
            )
        )
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .contentType(MediaType.APPLICATION_JSON)
            .body(body)
    }
}
package ru.deepthreads.rest.handlers

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import ru.deepthreads.rest.exceptions.other.AlreadyAMember
import ru.deepthreads.rest.exceptions.other.NotAMember
import ru.deepthreads.rest.exceptions.other.NotFound
import ru.deepthreads.rest.models.DebugFields
import ru.deepthreads.rest.models.response.errors.AlreadyMember
import ru.deepthreads.rest.models.response.errors.NotFoundResponse
import ru.deepthreads.rest.models.response.errors.NotMember
import java.io.FileNotFoundException
import java.lang.NullPointerException

@ControllerAdvice
class OtherHandlers : ResponseEntityExceptionHandler() {
    @ExceptionHandler(NotFound::class)
    fun handle(exception: NotFound, request: WebRequest): ResponseEntity<NotFoundResponse> {
        val body = NotFoundResponse(
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
    @ExceptionHandler(FileNotFoundException::class)
    fun handle(exception: FileNotFoundException, request: WebRequest): ResponseEntity<NotFoundResponse> {
        val body = NotFoundResponse(
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
    @ExceptionHandler(NullPointerException::class)
    fun handle(exception: NullPointerException, request: WebRequest): ResponseEntity<NotFoundResponse> {
        val body = NotFoundResponse(
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
    @ExceptionHandler(AlreadyAMember::class)
    fun handle(exception: AlreadyAMember, request: WebRequest): ResponseEntity<AlreadyMember> {
        val body = AlreadyMember(
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
    fun handle(exception: NotAMember, request: WebRequest): ResponseEntity<NotMember> {
        val body = NotMember(
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
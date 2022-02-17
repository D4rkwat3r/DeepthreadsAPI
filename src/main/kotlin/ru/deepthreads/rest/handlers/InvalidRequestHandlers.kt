package ru.deepthreads.rest.handlers

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import ru.deepthreads.rest.exceptions.other.InvalidRequest
import ru.deepthreads.rest.models.DebugFields
import ru.deepthreads.rest.models.response.errors.InvalidRequestResponse

@ControllerAdvice
class InvalidRequestHandlers : ResponseEntityExceptionHandler() {
    @ExceptionHandler(JsonParseException::class)
    fun handle(exception: JsonParseException, request: WebRequest): ResponseEntity<InvalidRequestResponse> {
        val body = InvalidRequestResponse(
            debugFields = DebugFields(
                exception::class.java.name,
                exception.message
            )
        )
        return ResponseEntity
            .badRequest()
            .contentType(MediaType.APPLICATION_JSON)
            .body(body)
    }
    @ExceptionHandler(MissingKotlinParameterException::class)
    fun handle(exception: MissingKotlinParameterException, request: WebRequest): ResponseEntity<InvalidRequestResponse> {
        val body = InvalidRequestResponse(
            debugFields = DebugFields(
                exception::class.java.name,
                exception.message
            )
        )
        return ResponseEntity
            .badRequest()
            .contentType(MediaType.APPLICATION_JSON)
            .body(body)
    }
    @ExceptionHandler(InvalidRequest::class)
    fun handle(exception: InvalidRequest, request: WebRequest): ResponseEntity<InvalidRequestResponse> {
        val body = InvalidRequestResponse(
            debugFields = DebugFields(
                exception::class.java.name,
                exception.message
            )
        )
        return ResponseEntity
            .badRequest()
            .contentType(MediaType.APPLICATION_JSON)
            .body(body)
    }
}
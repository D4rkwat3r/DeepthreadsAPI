package ru.deepthreads.rest.handlers

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import ru.deepthreads.rest.exceptions.dts.AccessDenied
import ru.deepthreads.rest.models.other.DebugFields
import ru.deepthreads.rest.models.response.errors.AccessDeniedResponse

@ControllerAdvice
class DTSHandlers : ResponseEntityExceptionHandler() {
    @ExceptionHandler(AccessDenied::class)
    fun handle(exception: AccessDenied, request: WebRequest): ResponseEntity<AccessDeniedResponse> {
        val body = AccessDeniedResponse(
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

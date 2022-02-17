package ru.deepthreads.rest.contollers.v1

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.deepthreads.rest.mapper
import ru.deepthreads.rest.models.requests.EventLogRequest
import ru.deepthreads.rest.models.response.success.EmptyOKResponse

@RestController @RequestMapping("/api/v1/event-log")
class EventController {
    @PostMapping("/activity-lifecycle-callback")
    fun acceptALC(
        @RequestBody body: String
    ): EmptyOKResponse {
        val data = mapper.readValue(body, EventLogRequest::class.java)
        // some actions...
        return EmptyOKResponse()
    }
}
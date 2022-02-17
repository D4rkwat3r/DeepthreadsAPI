package ru.deepthreads.rest.contollers.v1

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.deepthreads.rest.mapper
import ru.deepthreads.rest.models.requests.LoginRequest
import ru.deepthreads.rest.models.requests.RegisterRequest
import ru.deepthreads.rest.models.response.success.AccountResponse
import ru.deepthreads.rest.services.AccountService

@RestController @RequestMapping("/api/v1/auth")
class AuthController {

    @PostMapping("/login")
    fun login(
        @RequestBody body: String
    ): AccountResponse {
        val data = mapper.readValue(body, LoginRequest::class.java)
        val result = AccountService.validate(data.deepId, data.password)
        return AccountResponse(account = result)
    }

    @PostMapping("/register")
    fun register(
        @RequestBody body: String
    ): AccountResponse {
        val data = mapper.readValue(body, RegisterRequest::class.java)
        val result = AccountService.create(data.nickname, data.deepId, data.password, data.pictureResource)
        return AccountResponse(account = result)
    }
}
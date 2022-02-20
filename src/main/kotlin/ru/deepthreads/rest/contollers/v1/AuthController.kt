package ru.deepthreads.rest.contollers.v1

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.deepthreads.rest.Utils
import ru.deepthreads.rest.exceptions.auth.GoogleAuthFail
import ru.deepthreads.rest.mapper
import ru.deepthreads.rest.models.other.GoogleAuthException
import ru.deepthreads.rest.models.other.GoogleTokenInfo
import ru.deepthreads.rest.models.requests.GLoginRequest
import ru.deepthreads.rest.models.requests.LoginRequest
import ru.deepthreads.rest.models.requests.RegisterRequest
import ru.deepthreads.rest.models.response.success.AccountResponse
import ru.deepthreads.rest.services.AccountService
import ru.deepthreads.rest.util.HttpObj
import java.net.http.HttpClient

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
        val result = AccountService.create(data.nickname, Utils.safeDeepId(data.deepId), data.password, data.pictureResource)
        return AccountResponse(account = result)
    }

    @PostMapping("/g-login")
    fun googleLogin(
        @RequestBody body: String
    ): AccountResponse {
        val data = mapper.readValue(body, GLoginRequest::class.java)
        val response = HttpObj.get("https://oauth2.googleapis.com/tokeninfo?id_token=" + data.gToken)
        if (response.statusCode() != 200) {
            throw GoogleAuthFail(mapper.readValue(response.body(), GoogleAuthException::class.java))
        }
        val info = mapper.readValue(response.body(), GoogleTokenInfo::class.java)
        if (!AccountService.isEmailRegistered(info.email)) {
            val account = AccountService.create(
                info.name,
                info.email,
                null,
                info.picture ?: "http://deepthreads.ru/static/profile.jpg",
                info.email
            )
            return AccountResponse(account = account)
        }
        val account = AccountService.getByEmail(info.email)
        return AccountResponse(account = account)
    }
}
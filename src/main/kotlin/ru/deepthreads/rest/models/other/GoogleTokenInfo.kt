package ru.deepthreads.rest.models.other

import com.fasterxml.jackson.annotation.JsonProperty

data class GoogleTokenInfo(
    val iss: String,
    val azp: String,
    val aud: String,
    val sub: String,
    val email: String,
    @JsonProperty("email_verified")
    val emailVerified: Boolean,
    val name: String,
    val picture: String?,
    @JsonProperty("given_name")
    val givenName: String,
    val locale: String,
    val iat: String,
    val exp: String,
    val alg: String,
    val kid: String,
    @JsonProperty("typ")
    val type: String
)
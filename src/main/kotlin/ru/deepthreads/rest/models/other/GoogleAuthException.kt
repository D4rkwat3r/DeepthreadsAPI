package ru.deepthreads.rest.models.other

import com.fasterxml.jackson.annotation.JsonProperty

data class GoogleAuthException(
    val error: String,
    @JsonProperty("error_description")
    val errorDescription: String
)

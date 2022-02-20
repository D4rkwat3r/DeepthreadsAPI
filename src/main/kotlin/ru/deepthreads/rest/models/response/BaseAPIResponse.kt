package ru.deepthreads.rest.models.response

import ru.deepthreads.rest.models.other.DebugFields

interface BaseAPIResponse {
    val apiStatusCode: Int
    val apiMessage: String
    val debugFields: DebugFields?
}
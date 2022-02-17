package ru.deepthreads.rest.models.requests

data class NewThreadRequest(
    val name: String,
    val themePackColor: String,
    val themePackCoverResource: String,
    val themePackBackgroundResource: String
)

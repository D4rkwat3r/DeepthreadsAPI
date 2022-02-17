package ru.deepthreads.rest.models.requests

data class EventLogRequest(
    val activityName: String,
    val activitySimpleName: String,
    val lifecycleCallbackType: String
)

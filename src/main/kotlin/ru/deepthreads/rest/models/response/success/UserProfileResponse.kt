package ru.deepthreads.rest.models.response.success

import ru.deepthreads.rest.Constants
import ru.deepthreads.rest.models.DebugFields
import ru.deepthreads.rest.models.entity.UserProfile
import ru.deepthreads.rest.models.response.BaseAPIResponse

data class UserProfileResponse(
    override val apiStatusCode: Int = Constants.STATUSCODE.NORMAL,
    override val apiMessage: String = "OK",
    override val debugFields: DebugFields? = null,
    val userProfile: UserProfile
): BaseAPIResponse

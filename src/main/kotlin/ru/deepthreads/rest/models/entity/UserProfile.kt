package ru.deepthreads.rest.models.entity

import ru.deepthreads.rest.Constants

data class UserProfile(
    override val objectId: String,
    override val createdTime: Long,
    override val status: Int = Constants.OBJECTSTATUS.NORMAL,
    val nickname: String,
    val deepId: String,
    val pictureUrl: String,
    val email: String?,
    val subscriberUidList: List<String>,
    val blockerUidList: List<String>,
    val role: Int = Constants.ROLE.USER,
): BaseAPIEntity

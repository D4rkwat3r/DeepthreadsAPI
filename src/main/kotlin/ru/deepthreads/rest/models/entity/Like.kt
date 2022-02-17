package ru.deepthreads.rest.models.entity

data class Like(
    override val objectId: String,
    override val createdTime: Long,
    override val status: Int,
    val authorUid: String
): BaseAPIEntity

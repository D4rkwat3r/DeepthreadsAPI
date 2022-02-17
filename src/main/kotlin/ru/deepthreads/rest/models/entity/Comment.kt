package ru.deepthreads.rest.models.entity

data class Comment(
    override val objectId: String,
    override val createdTime: Long,
    override val status: Int,
    val authorUid: String,
    val content: String,
    val likes: List<Like>
): BaseAPIEntity

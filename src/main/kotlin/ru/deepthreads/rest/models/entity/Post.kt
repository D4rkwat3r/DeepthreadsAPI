package ru.deepthreads.rest.models.entity

data class Post(
    override val objectId: String,
    override val createdTime: Long,
    override val status: Int,
    val authorUid: String,
    val title: String,
    val content: String,
    val coverUrl: String,
    val backgroundUrl: String,
    val likes: List<Like>
): BaseAPIEntity

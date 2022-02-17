package ru.deepthreads.rest.models.entity

interface BaseAPIEntity {
    val objectId: String
    val status: Int
    val createdTime: Long
}
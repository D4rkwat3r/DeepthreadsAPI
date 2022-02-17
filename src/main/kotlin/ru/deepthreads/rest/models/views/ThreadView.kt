package ru.deepthreads.rest.models.views

import ru.deepthreads.rest.Constants
import ru.deepthreads.rest.models.entity.BaseAPIEntity

data class ThreadView(
    override val objectId: String,
    override val createdTime: Long,
    override val status: Int = Constants.OBJECTSTATUS.NORMAL,
    val name: String,
    val themeColor: String,
    val iconUrl: String,
    val coverUrl: String,
    val backgroundUrl: String
): BaseAPIEntity

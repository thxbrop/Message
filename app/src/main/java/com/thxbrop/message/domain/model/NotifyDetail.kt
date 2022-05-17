package com.thxbrop.message.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NotifyDetail(
    val name: String,
    @Notify.Type val type: Int,
    val photoUrl: String,
    val createdAt: Long,
    val updatedAt: Long,
    val lastMessageId: Int,
    @PrimaryKey(autoGenerate = true) val id: Int,
)

fun NotifyDetail.toNotify() = Notify(name, id, type)

package com.thxbrop.message.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Message(
    val content: String,
    val userId: Int,
    val timestamp: Long,
    val conId: Int,
    @PrimaryKey val id: Int
)

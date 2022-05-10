package com.thxbrop.message.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Message(
    val content: String,
    val from: Int,
    val createdAt: Long,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)

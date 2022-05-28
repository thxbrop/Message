package com.thxbrop.message.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Conversation(
    val name: String,
    val creatorId: Int,
    @PrimaryKey val id: Int,
)


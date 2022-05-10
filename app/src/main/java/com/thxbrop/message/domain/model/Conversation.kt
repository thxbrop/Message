package com.thxbrop.message.domain.model

import androidx.annotation.IntDef
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Conversation(
    val name: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ConversationType val type: Int = ConversationType.CHAT
)

@IntDef(
    ConversationType.PRIVATE,
    ConversationType.CHAT,
    ConversationType.GROUP,
    ConversationType.CHANNEL,
    ConversationType.NOTIFY
)
annotation class ConversationType {
    companion object {
        const val PRIVATE = 0
        const val CHAT = 1
        const val GROUP = 2
        const val CHANNEL = 3
        const val NOTIFY = 4
    }
}
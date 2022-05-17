package com.thxbrop.message.domain.model

import androidx.annotation.IntDef
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Notify(
    val name: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @Type val type: Int = Type.CHAT
) {
    @IntDef(
        Type.PRIVATE,
        Type.CHAT,
        Type.GROUP,
        Type.CHANNEL,
        Type.NOTIFY
    )
    annotation class Type {
        companion object {
            const val PRIVATE = 0
            const val CHAT = 1
            const val GROUP = 2
            const val CHANNEL = 3
            const val NOTIFY = 4
        }
    }
}


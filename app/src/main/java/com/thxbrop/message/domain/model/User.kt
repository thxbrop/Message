package com.thxbrop.message.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val name: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)

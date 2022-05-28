package com.thxbrop.message.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thxbrop.message.data.local.dao.ConversationDao
import com.thxbrop.message.data.local.dao.MessageDao
import com.thxbrop.message.data.local.dao.UserDao
import com.thxbrop.message.domain.model.Conversation
import com.thxbrop.message.domain.model.Message
import com.thxbrop.message.domain.model.User

@Database(
    entities = [Conversation::class, User::class, Message::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun conversationDao(): ConversationDao
    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao

}
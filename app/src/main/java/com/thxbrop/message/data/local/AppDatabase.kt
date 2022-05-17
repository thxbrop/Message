package com.thxbrop.message.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thxbrop.message.data.local.dao.MessageDao
import com.thxbrop.message.data.local.dao.NotifyDao
import com.thxbrop.message.data.local.dao.NotifyDetailDao
import com.thxbrop.message.data.local.dao.UserDao
import com.thxbrop.message.domain.model.Message
import com.thxbrop.message.domain.model.Notify
import com.thxbrop.message.domain.model.NotifyDetail
import com.thxbrop.message.domain.model.User

@Database(
    entities = [Notify::class, User::class, Message::class, NotifyDetail::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notifyDao(): NotifyDao
    abstract fun notifyDetailDao(): NotifyDetailDao
    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao

}
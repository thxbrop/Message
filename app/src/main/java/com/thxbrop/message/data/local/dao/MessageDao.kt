package com.thxbrop.message.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.thxbrop.message.domain.model.Message

@Dao
interface MessageDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(vararg message: Message)

    @Delete
    suspend fun delete(vararg message: Message)

    @Query("SELECT * FROM Message ORDER BY timestamp")
    suspend fun getAll(): List<Message>

    @Query("SELECT * FROM Message WHERE id = :id")
    suspend fun getById(id: Int): Message?
}
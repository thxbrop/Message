package com.thxbrop.message.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.thxbrop.message.domain.model.Conversation

@Dao
interface ConversationDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(vararg conversation: Conversation)

    @Delete
    suspend fun delete(vararg conversation: Conversation)

    @Query("SELECT * FROM Conversation")
    suspend fun getAll(): List<Conversation>

    @Query("SELECT * FROM Conversation WHERE id = :id")
    suspend fun getById(id: Int): Conversation?
}
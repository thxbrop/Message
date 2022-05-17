package com.thxbrop.message.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.thxbrop.message.domain.model.Notify

@Dao
interface NotifyDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(vararg notify: Notify)

    @Delete
    suspend fun delete(vararg notify: Notify)

    @Query("SELECT * FROM Notify")
    suspend fun getAll(): List<Notify>

    @Query("SELECT * FROM Notify WHERE id = :id")
    suspend fun getById(id: Int): Notify?
}
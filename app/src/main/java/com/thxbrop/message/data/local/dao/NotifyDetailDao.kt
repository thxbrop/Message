package com.thxbrop.message.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.thxbrop.message.domain.model.NotifyDetail

@Dao
interface NotifyDetailDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(vararg notifyDetail: NotifyDetail)

    @Delete
    suspend fun delete(vararg notifyDetail: NotifyDetail)

    @Query("SELECT * FROM NotifyDetail")
    suspend fun getAll(): List<NotifyDetail>

    @Query("SELECT * FROM NotifyDetail WHERE id = :id")
    suspend fun getById(id: Int): NotifyDetail?
}
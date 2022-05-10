package com.thxbrop.message.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.thxbrop.message.domain.model.User

@Dao
interface UserDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(vararg user: User)

    @Delete
    suspend fun delete(vararg user: User)

    @Query("SELECT * FROM User")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM User WHERE id = :id")
    suspend fun getById(id: Int): User?
}
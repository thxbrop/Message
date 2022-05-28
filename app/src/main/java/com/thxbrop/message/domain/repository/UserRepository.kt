package com.thxbrop.message.domain.repository

import com.thxbrop.message.Resource
import com.thxbrop.message.domain.model.Token
import com.thxbrop.message.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getById(id: Int): Flow<Resource<User>>
    suspend fun login(email: String, password: String): Flow<Resource<Token>>
    suspend fun register(
        email: String,
        password: String,
        username: String
    ): Flow<Resource<User>>

    suspend fun logout(): Flow<Resource<Any>>
}
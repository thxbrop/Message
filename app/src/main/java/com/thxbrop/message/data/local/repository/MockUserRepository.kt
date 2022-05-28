package com.thxbrop.message.data.local.repository

import com.thxbrop.message.Resource
import com.thxbrop.message.domain.model.Token
import com.thxbrop.message.domain.model.User
import com.thxbrop.message.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class MockUserRepository : UserRepository {
    override suspend fun getById(id: Int): Flow<Resource<User>> {
        TODO("Not yet implemented")
    }

    override suspend fun login(email: String, password: String): Flow<Resource<Token>> {
        TODO("Not yet implemented")
    }

    override suspend fun register(
        email: String,
        password: String,
        username: String
    ): Flow<Resource<User>> {
        TODO("Not yet implemented")
    }

    override suspend fun logout(): Flow<Resource<Any>> {
        TODO("Not yet implemented")
    }
}
package com.thxbrop.message.domain.repository

import com.thxbrop.message.Resource
import com.thxbrop.message.data.local.TokenManager
import com.thxbrop.message.data.local.dao.UserDao
import com.thxbrop.message.data.remote.UserService
import com.thxbrop.message.domain.model.Token
import com.thxbrop.message.domain.model.User
import com.thxbrop.message.extensions.sandBox
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val userService: UserService, private val userDao: UserDao
) : UserRepository {
    override suspend fun getById(id: Int): Flow<Resource<User>> = flow {
        emit(Resource.Loading)
        sandBox {
            val result = userService.getById(id)
            result.data?.let { user ->
                userDao.insert(user)
                userDao.getById(id)?.let { emit(Resource.Success(it)) }
            }
            result.message?.let {
                emit(Resource.Failure(it))
            }
        }

    }

    override suspend fun login(email: String, password: String): Flow<Resource<Token>> = flow {
        emit(Resource.Loading)
        sandBox {
            val result = userService.login(email, password)
            result.data?.let { token ->
                TokenManager.put(token.userId, token.token)
                emit(Resource.Success(token))
            }
            result.message?.let {
                emit(Resource.Failure(it))
            }
        }

    }

    override suspend fun register(
        email: String, password: String, username: String
    ): Flow<Resource<User>> = flow {
        emit(Resource.Loading)
        sandBox {
            val result = userService.register(email, username, password)
            result.data?.let { user ->
                emit(Resource.Success(user))
            }
            result.message?.let {
                emit(Resource.Failure(it))
            }
        }
    }

    override suspend fun logout(): Flow<Resource<Any>> = flow {
        emit(Resource.Loading)
        TokenManager.remove()
        emit(Resource.Success(""))
    }
}
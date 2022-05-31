package com.thxbrop.message.domain.repository

import com.thxbrop.message.Resource
import com.thxbrop.message.data.local.TokenManager
import com.thxbrop.message.data.local.dao.UserDao
import com.thxbrop.message.data.remote.UserService
import com.thxbrop.message.domain.model.Token
import com.thxbrop.message.domain.model.User
import com.thxbrop.message.sandbox
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val userService: UserService, private val userDao: UserDao
) : UserRepository {
    override suspend fun getById(id: Int): Flow<Resource<User>> = flow {
        emit(Resource.Loading)
        sandbox {
            val result = userService.getById(id)
            result.handleData { user ->
                userDao.insert(user)
                userDao.getById(id)?.let { emit(Resource.Success(it)) }
            }
            result.handleError { code, message ->
                emit(Resource.Failure(code, message))
            }
        }

    }

    override suspend fun login(email: String, password: String): Flow<Resource<Token>> = flow {
        emit(Resource.Loading)
        sandbox {
            val result = userService.login(email, password)
            result.handleData { token ->
                TokenManager.setToken(token.token)
                TokenManager.setUserId(token.userId)
                emit(Resource.Success(token))
            }
            result.handleError { code, message ->
                emit(Resource.Failure(code, message))
            }
        }

    }

    override suspend fun register(
        email: String, password: String, username: String
    ): Flow<Resource<User>> = flow {
        emit(Resource.Loading)
        sandbox {
            val result = userService.register(email, username, password)
            result.handleData { user ->
                emit(Resource.Success(user))
            }
            result.handleError { code, message ->
                emit(Resource.Failure(code, message))
            }
        }
    }

    override suspend fun logout(): Flow<Resource<Any>> = flow {
        emit(Resource.Loading)
        TokenManager.remove()
        emit(Resource.Success(""))
    }
}
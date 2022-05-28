package com.thxbrop.message.data.remote

import com.thxbrop.message.Result
import com.thxbrop.message.domain.model.Token
import com.thxbrop.message.domain.model.User
import retrofit2.http.GET
import retrofit2.http.Part
import retrofit2.http.Query

interface UserService {
    @GET("/user/login")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Result<Token>

    @GET("/user/register")
    suspend fun register(
        @Query("email") email: String,
        @Query("username") username: String,
        @Query("password") password: String
    ): Result<User>

    @GET("/user/{id}")
    suspend fun getById(
        @Part("id") id: Int,
    ): Result<User>
}
package com.thxbrop.message.data.remote

import com.thxbrop.message.data.remote.dto.Result
import com.thxbrop.message.domain.model.Message
import com.thxbrop.message.domain.model.NotifyDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NotifyService {
    @GET("notify/{id}")
    suspend fun getNotifyDetail(@Path("id") id: Int): Result<NotifyDetail>

    @GET("notify/{id}")
    suspend fun getMessages(@Path("id") id: Int, @Query("limit") limit: Int): Result<List<Message>>

    @GET("notify/send/{id}")
    suspend fun sendMessage(
        @Path("id") id: Int,
        @Query("senderId") senderId: Int,
        @Query("content") content: String
    ): Int
}
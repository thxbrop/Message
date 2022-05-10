package com.thxbrop.message.data.remote

import com.thxbrop.message.domain.model.Conversation
import retrofit2.http.GET
import retrofit2.http.Path

interface AccountService {
    @GET("account/conversations/{userId}")
    suspend fun getConversations(@Path("userId") userId: Int): List<Conversation>
}
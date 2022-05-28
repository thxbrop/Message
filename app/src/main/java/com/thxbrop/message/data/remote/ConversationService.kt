package com.thxbrop.message.data.remote

import com.thxbrop.message.Result
import com.thxbrop.message.domain.model.Conversation
import com.thxbrop.message.domain.model.Invite
import com.thxbrop.message.domain.model.Member
import com.thxbrop.message.domain.model.Message
import retrofit2.http.GET
import retrofit2.http.Part
import retrofit2.http.Query

interface ConversationService {
    @GET("/con/{id}")
    suspend fun getById(@Part("id") id: Int): Result<Conversation>

    @GET("/con/members/{conId}")
    suspend fun getMembersById(@Part("conId") conId: Int): Result<List<Member>>

    @GET("/con/member/{id}")
    suspend fun getMemberById(@Part("id") id: Int): Result<Member>

    @GET("/con/memberBlur")
    suspend fun getMemberBlur(
        @Query("conId") conId: Int,
        @Query("userId") userId: Int
    ): Result<Member>

    @GET("/con/message/{conId}")
    suspend fun getMessages(
        @Part("conId") conId: Int,
        @Query("limit") limit: Int = 20
    ): Result<List<Message>>

    @GET("/con/save")
    suspend fun save(
        @Query("name") name: String,
        @Query("creatorId") creatorId: Int
    ): Result<Conversation>

    @GET("/con/send")
    suspend fun send(
        @Query("conId") conId: Int,
        @Query("userId") userId: Int,
        @Query("content") content: String
    ): Result<Message>

    @GET("/con/invite/{conId}")
    suspend fun invite(
        @Part("conId") conId: Int,
        @Query("from") from: Int,
        @Query("to") to: Int
    ): Result<Invite>

    @GET("/con/query/contains")
    suspend fun getConversationsContains(
        @Query("userId") userId: Int
    ): Result<List<Conversation>>

}
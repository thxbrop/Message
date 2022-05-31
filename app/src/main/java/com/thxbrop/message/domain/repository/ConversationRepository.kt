package com.thxbrop.message.domain.repository

import com.thxbrop.message.Resource
import com.thxbrop.message.domain.model.Conversation
import kotlinx.coroutines.flow.Flow

interface ConversationRepository {
    fun getConversationsContainsFlow(): Flow<Resource<List<Conversation>>>
    fun getConversationByIdFlow(): Flow<Resource<Conversation>>
    suspend fun fetchConversationById(id: Int)
    suspend fun fetchConversationsContains(userId: Int)
}
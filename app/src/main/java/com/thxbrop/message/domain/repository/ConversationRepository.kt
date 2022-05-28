package com.thxbrop.message.domain.repository

import com.thxbrop.message.Resource
import com.thxbrop.message.domain.model.Conversation
import kotlinx.coroutines.flow.Flow

interface ConversationRepository {
    suspend fun getConversationById(id: Int): Flow<Resource<Conversation>>
    suspend fun getConversationsContains(userId: Int): Flow<Resource<List<Conversation>>>
}
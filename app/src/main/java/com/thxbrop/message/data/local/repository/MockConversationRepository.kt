package com.thxbrop.message.data.local.repository

import com.thxbrop.message.Resource
import com.thxbrop.message.domain.model.Conversation
import com.thxbrop.message.domain.repository.ConversationRepository
import kotlinx.coroutines.flow.Flow

class MockConversationRepository : ConversationRepository {
    override suspend fun fetchConversationById(id: Int) {

    }

    override suspend fun fetchConversationsContains(userId: Int) {

    }

    override fun getConversationsContainsFlow(): Flow<Resource<List<Conversation>>> {
        TODO("Not yet implemented")
    }

    override fun getConversationByIdFlow(): Flow<Resource<Conversation>> {
        TODO("Not yet implemented")
    }
}
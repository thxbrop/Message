package com.thxbrop.message.data.local.repository

import com.thxbrop.message.Resource
import com.thxbrop.message.domain.model.Conversation
import com.thxbrop.message.domain.repository.ConversationRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockConversationRepository : ConversationRepository {
    override suspend fun getConversationById(id: Int): Flow<Resource<Conversation>> = flow {
        emit(Resource.Loading)
        delay(1000)
        Conversation(
            name = "TestConversation",
            creatorId = 1,
            id = 1
        ).also { emit(Resource.Success(it)) }
    }

    override suspend fun getConversationsContains(userId: Int): Flow<Resource<List<Conversation>>> =
        flow {
            emit(Resource.Loading)
            delay(1000)
            listOf(
                Conversation(
                    name = "TestConversation",
                    creatorId = 1,
                    id = 1
                )
            ).also { emit(Resource.Success(it)) }
        }
}
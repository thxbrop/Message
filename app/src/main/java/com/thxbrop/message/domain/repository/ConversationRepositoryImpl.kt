package com.thxbrop.message.domain.repository

import com.thxbrop.message.Resource
import com.thxbrop.message.data.local.dao.ConversationDao
import com.thxbrop.message.data.remote.ConversationService
import com.thxbrop.message.domain.model.Conversation
import com.thxbrop.message.extensions.sandBox
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class ConversationRepositoryImpl(
    private val conversationService: ConversationService,
    private val conversationDao: ConversationDao,
) : ConversationRepository {
    override suspend fun getConversationById(id: Int): Flow<Resource<Conversation>> = flow {
        emit(Resource.Loading)
        sandBox {
            withContext(Dispatchers.IO) {
                val result = conversationService.getById(id)
                result.message?.let {
                    emit(Resource.Failure(it))
                }
                result.data?.let {
                    conversationDao.insert(it)
                    emit(Resource.Success(it))
                }
            }
        }

    }

    override suspend fun getConversationsContains(userId: Int): Flow<Resource<List<Conversation>>> =
        flow {
            emit(Resource.Loading)
            sandBox {
                withContext(Dispatchers.IO) {
                    val result = conversationService.getConversationsContains(userId)
                    result.message?.let {
                        emit(Resource.Failure(it))
                    }
                    result.data?.let {
                        conversationDao.insert(*it.toTypedArray())
                        emit(Resource.Success(it))
                    }
                }
            }
        }
}
package com.thxbrop.message.domain.repository

import com.thxbrop.message.Resource
import com.thxbrop.message.data.local.dao.ConversationDao
import com.thxbrop.message.data.remote.ConversationService
import com.thxbrop.message.domain.model.Conversation
import com.thxbrop.message.sandbox
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

class ConversationRepositoryImpl(
    private val conversationService: ConversationService,
    private val conversationDao: ConversationDao,
) : ConversationRepository {
    private val _conversationsStateFlow = MutableStateFlow<Resource<List<Conversation>>>(Resource.Loading)
    private val _conversationStateFlow = MutableStateFlow<Resource<Conversation>>(Resource.Loading)

    override suspend fun fetchConversationById(id: Int) {
        with(_conversationStateFlow) {
            emit(Resource.Loading)
            sandbox {
                val result = conversationService.getById(id)
                result.handleError { code, message ->
                    emit(Resource.Failure(code, message))
                }
                result.handleData {
                    conversationDao.insert(it)
                    emit(Resource.Success(it))
                }
            }
        }
    }

    override suspend fun fetchConversationsContains(userId: Int) {
        with(_conversationsStateFlow) {
            emit(Resource.Loading)
            sandbox {
                val result = conversationService.getConversationsContains(userId)
                result.handleError { code, message ->
                    emit(Resource.Failure(code, message))
                }
                result.handleData {
                    conversationDao.insert(*it.toTypedArray())
                    emit(Resource.Success(it))
                }
            }
        }
    }

    override fun getConversationsContainsFlow(): Flow<Resource<List<Conversation>>> {
        return _conversationsStateFlow
    }

    override fun getConversationByIdFlow(): Flow<Resource<Conversation>> {
        return _conversationStateFlow
    }
}
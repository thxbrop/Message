package com.thxbrop.message.domain.repository

import com.thxbrop.message.domain.model.Conversation

interface AccountRepository {
    suspend fun getConversations(): List<Conversation>
}
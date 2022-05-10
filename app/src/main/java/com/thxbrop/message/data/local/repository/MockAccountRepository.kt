package com.thxbrop.message.data.local.repository

import com.thxbrop.message.R
import com.thxbrop.message.application
import com.thxbrop.message.domain.model.Conversation
import com.thxbrop.message.domain.repository.AccountRepository

class MockAccountRepository : AccountRepository {
    override suspend fun getConversations(): List<Conversation> {
        return listOf(
            Conversation(name = application.getString(R.string.item_notify_title), 0),
            Conversation(name = application.getString(R.string.item_notify_title), 1),
            Conversation(name = application.getString(R.string.item_notify_title), 2),
            Conversation(name = application.getString(R.string.item_notify_title), 3),
        )
    }
}
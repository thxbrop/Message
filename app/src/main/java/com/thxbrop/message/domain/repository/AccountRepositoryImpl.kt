package com.thxbrop.message.domain.repository

import com.thxbrop.message.data.local.storage.LocalStorage
import com.thxbrop.message.data.remote.AccountService
import com.thxbrop.message.domain.model.Conversation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccountRepositoryImpl(
    private val accountService: AccountService,
    private val localStorage: LocalStorage,
) : AccountRepository {
    override suspend fun getConversations(): List<Conversation> {
        return withContext(Dispatchers.IO) {
            val localUser = localStorage.getLocalUser()
            if (localUser != null) {
                val conversations = accountService.getConversations(localUser.id)
                localStorage.saveConversation(*conversations.toTypedArray())
            }
            localStorage.getMyConversations()
        }
    }
}
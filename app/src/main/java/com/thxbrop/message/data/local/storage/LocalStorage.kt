package com.thxbrop.message.data.local.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.thxbrop.message.application
import com.thxbrop.message.data.local.dao.ConversationDao
import com.thxbrop.message.data.local.dao.UserDao
import com.thxbrop.message.domain.model.Conversation
import com.thxbrop.message.domain.model.User

class LocalStorage constructor(
    private val conversationDao: ConversationDao,
    private val userDao: UserDao
) {
    companion object {
        private const val SP_ACCOUNT = "sp:account"
        private const val SP_CONVERSATION = "sp:conversation"
        private const val KEY_CONVERSATION_SET = "conversation_set"
        private const val KEY_LOCAL_USER = "local_user"
    }

    private val accountSharedPreferences: SharedPreferences =
        application.getSharedPreferences(SP_ACCOUNT, Context.MODE_PRIVATE)
    private val conversationSharedPreferences: SharedPreferences =
        application.getSharedPreferences(SP_CONVERSATION, Context.MODE_PRIVATE)

    suspend fun getMyConversations() =
        conversationSharedPreferences.getStringSet(KEY_CONVERSATION_SET, emptySet())!!
            .mapNotNull { conversationDao.getById(it.toInt()) }

    suspend fun getLocalUser() =
        accountSharedPreferences.getInt(KEY_LOCAL_USER, -1).let {
            if (it == -1) null
            else userDao.getById(it)
        }

    suspend fun saveConversation(vararg conversation: Conversation) {
        conversationDao.insert(*conversation)
        conversationSharedPreferences.edit {
            putStringSet(KEY_CONVERSATION_SET, conversation.map { it.id.toString() }.toSet())
        }
    }

    suspend fun changeLocalUser(user: User) {
        userDao.insert(user)
        accountSharedPreferences.edit {
            putInt(KEY_LOCAL_USER, user.id)
        }
    }

    fun removeLocalUser() {
        accountSharedPreferences.edit {
            remove(KEY_LOCAL_USER)
        }
    }
}
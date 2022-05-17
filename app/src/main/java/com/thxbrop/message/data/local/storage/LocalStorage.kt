package com.thxbrop.message.data.local.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.thxbrop.message.application
import com.thxbrop.message.data.local.dao.NotifyDao
import com.thxbrop.message.data.local.dao.UserDao
import com.thxbrop.message.domain.model.Notify
import com.thxbrop.message.domain.model.User

class LocalStorage constructor(
    private val notifyDao: NotifyDao,
    private val userDao: UserDao
) {
    companion object {
        private const val SP_ACCOUNT = "sp:account"
        private const val SP_NOTIFY = "sp:notify"
        private const val KEY_NOTIFY_SET = "notify_set"
        private const val KEY_LOCAL_USER = "local_user"

        private const val RES_USER_NOT_FOUND = -1
    }

    private val accountSharedPreferences: SharedPreferences =
        application.getSharedPreferences(SP_ACCOUNT, Context.MODE_PRIVATE)
    private val notifySharedPreferences: SharedPreferences =
        application.getSharedPreferences(SP_NOTIFY, Context.MODE_PRIVATE)

    suspend fun getMyNotify() =
        notifySharedPreferences.getStringSet(KEY_NOTIFY_SET, emptySet())!!
            .mapNotNull { notifyDao.getById(it.toInt()) }

    suspend fun getLocalUser() =
        accountSharedPreferences.getInt(KEY_LOCAL_USER, RES_USER_NOT_FOUND).let {
            if (it == RES_USER_NOT_FOUND) null
            else userDao.getById(it)
        }

    suspend fun saveNotify(vararg notify: Notify) {
        notifyDao.insert(*notify)
        notifySharedPreferences.edit {
            putStringSet(KEY_NOTIFY_SET, notify.map { it.id.toString() }.toSet())
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
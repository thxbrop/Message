package com.thxbrop.message.data.local

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.thxbrop.message.application
import com.thxbrop.message.extensions.accountDataStore
import kotlinx.coroutines.flow.map

object TokenManager {
    suspend fun put(userId: Int, token: String) {
        application.accountDataStore.edit {
            it[intPreferencesKey("userId")] = userId
            it[stringPreferencesKey("token")] = token
        }
    }

    fun get(receiver: (Int?, String?) -> Unit) {
        application.accountDataStore.data.map {
            receiver.invoke(it[intPreferencesKey("userId")], it[stringPreferencesKey("token")])
        }
    }

    suspend fun remove() {
        application.accountDataStore.edit {
            it.remove(intPreferencesKey("userId"))
            it.remove(stringPreferencesKey("token"))
        }
    }
}
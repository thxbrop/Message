package com.thxbrop.message.extensions

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.accountDataStore: DataStore<Preferences> by preferencesDataStore("account")
val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore("settings")
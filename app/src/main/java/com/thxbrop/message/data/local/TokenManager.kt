package com.thxbrop.message.data.local

import com.thxbrop.message.mmkv

object TokenManager {

    fun remove() {
        mmkv.remove("userId")
        mmkv.remove("token")
    }

    val token = mmkv.decodeString("token")
    val userId = mmkv.decodeInt("userId")
    val hasCached = token != null

    fun setToken(token: String) {
        mmkv.encode("token", token)
    }

    fun setUserId(userId: Int) {
        mmkv.encode("userId", userId)
    }
}
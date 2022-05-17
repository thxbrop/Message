package com.thxbrop.message.domain.repository

import com.thxbrop.message.domain.model.Notify

interface AccountRepository {
    suspend fun getNotifies(): List<Notify>
    suspend fun loginByMobilePhoneNumber(phoneNumber: Long, code: Long): String
}
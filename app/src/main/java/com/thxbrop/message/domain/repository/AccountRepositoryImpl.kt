package com.thxbrop.message.domain.repository

import com.thxbrop.message.data.local.storage.LocalStorage
import com.thxbrop.message.data.remote.AccountService
import com.thxbrop.message.domain.model.Notify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccountRepositoryImpl(
    private val accountService: AccountService,
    private val localStorage: LocalStorage,
) : AccountRepository {
    override suspend fun loginByMobilePhoneNumber(phoneNumber: Long, code: Long): String {
        return withContext(Dispatchers.IO) {
            val result =
                accountService.loginByMobilePhoneNumber(phoneNumber, code)
            result.data?.let {
                return@withContext it
            }
            result.message?.let { throw Exception(it) }
            return@withContext ""
        }
    }

    override suspend fun getNotifies(): List<Notify> {
        return withContext(Dispatchers.IO) {
            val localUser = localStorage.getLocalUser()
            if (localUser != null) {
                val result = accountService.getNotifies(localUser.id)
                result.data?.let {
                    localStorage.saveNotify(*it.toTypedArray())
                }
                result.message?.let { throw Exception(it) }
            }
            localStorage.getMyNotify()
        }
    }
}
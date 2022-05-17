package com.thxbrop.message.data.local.repository

import com.thxbrop.message.R
import com.thxbrop.message.application
import com.thxbrop.message.domain.model.Notify
import com.thxbrop.message.domain.repository.AccountRepository
import kotlinx.coroutines.delay

class MockAccountRepository : AccountRepository {
    override suspend fun getNotifies(): List<Notify> {
        return listOf(
            Notify(name = application.getString(R.string.item_notify_title), 0),
            Notify(name = application.getString(R.string.item_notify_title), 1),
            Notify(name = application.getString(R.string.item_notify_title), 2),
            Notify(name = application.getString(R.string.item_notify_title), 3),
        )
    }

    override suspend fun loginByMobilePhoneNumber(phoneNumber: Long, code: Long): String {
        delay(2000)
        return "验证码已发送至您的手机"
    }
}
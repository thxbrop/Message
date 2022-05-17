package com.thxbrop.message.data.local.repository

import com.thxbrop.message.R
import com.thxbrop.message.application
import com.thxbrop.message.domain.model.Notify
import com.thxbrop.message.domain.model.NotifyDetail
import com.thxbrop.message.domain.repository.NotifyRepository

class MockNotifyRepository : NotifyRepository {
    override suspend fun getNotifyDetail(id: Int): NotifyDetail {
        return NotifyDetail(
            name = application.getString(R.string.item_notify_title),
            type = Notify.Type.CHAT,
            photoUrl = "",
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis(),
            lastMessageId = 1,
            id = 1
        )
    }
}
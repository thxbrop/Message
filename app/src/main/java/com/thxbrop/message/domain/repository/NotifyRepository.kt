package com.thxbrop.message.domain.repository

import com.thxbrop.message.domain.model.NotifyDetail

interface NotifyRepository {
    suspend fun getNotifyDetail(id: Int): NotifyDetail
}
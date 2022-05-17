package com.thxbrop.message.domain.repository

import com.thxbrop.message.data.local.dao.NotifyDao
import com.thxbrop.message.data.local.dao.NotifyDetailDao
import com.thxbrop.message.data.remote.NotifyService
import com.thxbrop.message.domain.model.NotifyDetail
import com.thxbrop.message.domain.model.toNotify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotifyRepositoryImpl(
    private val notifyService: NotifyService,
    private val notifyDao: NotifyDao,
    private val notifyDetailDao: NotifyDetailDao
) : NotifyRepository {
    override suspend fun getNotifyDetail(id: Int): NotifyDetail {
        return withContext(Dispatchers.IO) {
            val result = notifyService.getNotifyDetail(id)
            result.message?.let { throw Exception(it) }
            result.data?.let {
                notifyDao.insert(it.toNotify())
                notifyDetailDao.insert(it)
            }
            notifyDetailDao.getById(id)!!
        }
    }
}
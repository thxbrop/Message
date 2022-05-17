package com.thxbrop.message.domain.use_case

import com.thxbrop.message.Resource
import com.thxbrop.message.domain.model.NotifyDetail
import com.thxbrop.message.domain.repository.NotifyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

data class GetNotifyDetailUseCase @Inject constructor(
    val notifyRepository: NotifyRepository
) {
    suspend operator fun invoke(id: Int): Flow<Resource<NotifyDetail>> = flow {
        emit(Resource.Loading)
        try {
            val notifyDetail = notifyRepository.getNotifyDetail(id)
            emit(Resource.Success(notifyDetail))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}

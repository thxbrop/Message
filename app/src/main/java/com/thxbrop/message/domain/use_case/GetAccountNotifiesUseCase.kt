package com.thxbrop.message.domain.use_case

import com.thxbrop.message.Resource
import com.thxbrop.message.domain.model.Notify
import com.thxbrop.message.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

data class GetAccountNotifiesUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Notify>>> = flow {
        emit(Resource.Loading)
        try {
            val conversations = accountRepository.getNotifies()
            emit(Resource.Success(conversations))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}

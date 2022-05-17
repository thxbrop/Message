package com.thxbrop.message.domain.use_case

import com.thxbrop.message.Resource
import com.thxbrop.message.domain.repository.AccountRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

data class LoginByMobilePhoneNumberUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    private val _flow = MutableSharedFlow<Resource<String>>()
    val flow = _flow.asSharedFlow()
    suspend operator fun invoke(
        phoneNumber: Long,
        code: Long
    ) {
        with(_flow) {
            emit(Resource.Loading)
            try {
                val data = accountRepository.loginByMobilePhoneNumber(phoneNumber, code)
                emit(Resource.Success(data))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }
}


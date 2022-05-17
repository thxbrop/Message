package com.thxbrop.message.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thxbrop.message.Resource
import com.thxbrop.message.domain.repository.AccountRepository
import com.thxbrop.message.extensions.asPhoneNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val _loginChanel = Channel<Resource<String>>()
    val loginFlow = _loginChanel.receiveAsFlow()

    fun loginByPhoneNumber(mobilePhoneNumber: String) {
        viewModelScope.launch {
            with(_loginChanel) {
                send(Resource.Loading)
                try {
                    val data = with(mobilePhoneNumber.asPhoneNumber()) {
                        accountRepository.loginByMobilePhoneNumber(code, number)
                    }
                    send(Resource.Success(data))
                } catch (e: Exception) {
                    send(Resource.Failure(e))
                }
            }
        }
    }
}
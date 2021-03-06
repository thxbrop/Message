package com.thxbrop.message.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thxbrop.message.Resource
import com.thxbrop.message.domain.model.Token
import com.thxbrop.message.domain.model.User
import com.thxbrop.message.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private var _loginFlow = MutableSharedFlow<Resource<Token>>()
    val loginFlow = _loginFlow.asSharedFlow()
    private var _registerFlow = MutableSharedFlow<Resource<User>>()
    val registerFlow = _registerFlow.asSharedFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            userRepository.login(email, password).collectLatest {
                _loginFlow.emit(it)
            }
        }
    }

    fun register(
        email: String,
        password: String,
        username: String
    ) {
        viewModelScope.launch {
            userRepository.register(email, password, username).collectLatest {
                _registerFlow.emit(it)
            }
        }
    }
}
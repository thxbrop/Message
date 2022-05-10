package com.thxbrop.message.presentation.notify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thxbrop.message.Resource
import com.thxbrop.message.domain.model.Conversation
import com.thxbrop.message.domain.use_case.GetAccountConversationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotifyViewModel @Inject constructor(
    private val getAccountConversationsUseCase: GetAccountConversationsUseCase
) : ViewModel() {
    private var _conversationsState =
        MutableStateFlow<Resource<List<Conversation>>>(Resource.Success(emptyList()))
    val conversationsState: StateFlow<Resource<List<Conversation>>> get() = _conversationsState
    fun getConversations() {
        viewModelScope.launch {
            getAccountConversationsUseCase().collectLatest {
                _conversationsState.emit(it)
            }
        }
    }
}
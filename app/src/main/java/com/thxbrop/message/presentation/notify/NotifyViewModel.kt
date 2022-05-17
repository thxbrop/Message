package com.thxbrop.message.presentation.notify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thxbrop.message.Resource
import com.thxbrop.message.domain.model.Notify
import com.thxbrop.message.domain.use_case.GetAccountNotifiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotifyViewModel @Inject constructor(
    private val getAccountNotifiesUseCase: GetAccountNotifiesUseCase
) : ViewModel() {
    private var _conversationsFlow =
        MutableStateFlow<Resource<List<Notify>>?>(null)
    val conversationsFlow: Flow<Resource<List<Notify>>> get() = _conversationsFlow.mapNotNull { it }
    fun getNotifies() {
        viewModelScope.launch {
            getAccountNotifiesUseCase().collectLatest {
                _conversationsFlow.emit(it)
            }
        }
    }
}
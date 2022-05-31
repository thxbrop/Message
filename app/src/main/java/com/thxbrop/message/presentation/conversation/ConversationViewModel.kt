package com.thxbrop.message.presentation.conversation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thxbrop.message.Resource
import com.thxbrop.message.domain.model.Conversation
import com.thxbrop.message.domain.repository.ConversationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationViewModel @Inject constructor(
    private val conversationRepository: ConversationRepository,
) : ViewModel() {

    fun getConversations(userId: Int) {
        viewModelScope.launch {
            conversationRepository.fetchConversationsContains(userId)
        }
    }

    private var oldList = emptyList<Conversation>()

    private val _uiState = MutableStateFlow(UIState(false))
    val uiState = _uiState.asStateFlow()

    init {
        val flow = conversationRepository.getConversationsContainsFlow()
        viewModelScope.launch {
            flow.map { resource ->
                when (resource) {
                    Resource.Loading -> UIState(true)
                    is Resource.Success -> {
                        oldList = resource.data
                        UIState(
                            loading = false,
                            conversations = resource.data
                        )
                    }
                    is Resource.Failure -> UIState(
                        loading = false,
                        conversations = oldList,
                        errorMsg = resource.message
                    )
                }
            }.collectLatest {
                _uiState.value = it
            }
        }
    }
}
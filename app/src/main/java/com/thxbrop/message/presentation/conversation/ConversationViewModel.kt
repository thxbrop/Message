package com.thxbrop.message.presentation.conversation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thxbrop.message.Resource
import com.thxbrop.message.data.local.TokenManager
import com.thxbrop.message.domain.model.Conversation
import com.thxbrop.message.domain.repository.ConversationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationViewModel @Inject constructor(
    private val conversationRepository: ConversationRepository,
) : ViewModel() {
    private var _conversationsLiveData = MutableLiveData<Resource<List<Conversation>>>()
    val conversationLiveData: LiveData<Resource<List<Conversation>>> = _conversationsLiveData


    fun getConversations() {
        TokenManager.get { id, _ ->
            if (id != null) {
                viewModelScope.launch {
                    conversationRepository.getConversationsContains(id)
                }
            }
        }
    }

}
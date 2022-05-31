package com.thxbrop.message.presentation.conversation

import com.thxbrop.message.domain.model.Conversation

data class UIState(
    val loading: Boolean,
    val conversations: List<Conversation> = emptyList(),
    val errorMsg: String? = null
)

package com.thxbrop.message.presentation.message

import androidx.lifecycle.ViewModel
import com.thxbrop.message.domain.use_case.GetNotifyDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val getNotifyDetailUseCase: GetNotifyDetailUseCase
) : ViewModel() {

}
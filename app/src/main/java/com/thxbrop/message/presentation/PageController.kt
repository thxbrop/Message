package com.thxbrop.message.presentation

import androidx.annotation.MainThread
import com.thxbrop.message.R
import com.thxbrop.message.application

interface PageController<T> {
    @MainThread
    fun animateTitle(title: String = application.getString(R.string.app_name))

    @MainThread
    fun setTitle(title: String = application.getString(R.string.app_name))

    @MainThread
    fun submitList(list: List<T>)
}
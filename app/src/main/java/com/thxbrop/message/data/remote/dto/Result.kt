package com.thxbrop.message.data.remote.dto

data class Result<out T>(
    val data: T? = null,
    val message: String? = null
)

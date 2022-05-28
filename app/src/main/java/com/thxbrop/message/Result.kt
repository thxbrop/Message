package com.thxbrop.message

data class Result<out T>(
    val data: T? = null,
    val message: String? = null
) {
    fun isSuccess() = data != null
}

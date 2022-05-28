package com.thxbrop.message

sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    object Loading : Resource<Nothing>()
    data class Failure(val message: String) : Resource<Nothing>()
}

package com.thxbrop.message

import com.thxbrop.message.data.remote.ServerException
import kotlinx.coroutines.flow.FlowCollector

sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    object Loading : Resource<Nothing>()
    data class Failure(val code: Int, val message: String) : Resource<Nothing>()
}

/**
 * Catch the suspend code block's error and emit its message as Resource.Failure
 * @param defaultErrorMessage The default error message if error's message is null
 * @param block The suspend code block expended the Resource FlowCollector
 */
suspend fun <T> FlowCollector<Resource<T>>.sandbox(
    defaultErrorMessage: String = "Unknown Error",
    block: suspend FlowCollector<Resource<T>>.() -> Unit
) {
    try {
        block.invoke(this)
    } catch (e: ServerException) {
        emit(Resource.Failure(e.code, e.message ?: defaultErrorMessage))
    } catch (e: Exception) {
        emit(Resource.Failure(999, e.message ?: defaultErrorMessage))
    }
}
package com.thxbrop.message.extensions

import com.thxbrop.message.Resource
import kotlinx.coroutines.flow.FlowCollector

suspend fun <T> FlowCollector<Resource<T>>.sandBox(block: suspend FlowCollector<Resource<T>>.() -> Unit) {
    try {
        block.invoke(this)
    } catch (e: Exception) {
        emit(Resource.Failure(e.message ?: "Unknown Error"))
    }
}
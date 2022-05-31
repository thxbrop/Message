package com.thxbrop.message

data class Result<out T>(
    val data: T? = null,
    val message: String? = null,
    val code: Int
) {
    suspend fun handleError(block: suspend (Int, String) -> Unit) {
        if (code != 200) {
            block.invoke(code, message!!)
        }
    }

    suspend fun handleData(block: suspend (T) -> Unit) {
        if (code == 200) {
            block.invoke(data!!)
        }
    }
}

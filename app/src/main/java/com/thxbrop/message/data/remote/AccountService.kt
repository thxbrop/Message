package com.thxbrop.message.data.remote

import com.thxbrop.message.data.remote.dto.Result
import com.thxbrop.message.domain.model.Notify
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AccountService {
    @GET("account/notify/{userId}")
    suspend fun getNotifies(@Path("userId") userId: Int): Result<List<Notify>>

    @GET("account/login")
    suspend fun loginByMobilePhoneNumber(
        @Query("phone") phone: Long,
        @Query("code") code: Long
    ): Result<String>
}
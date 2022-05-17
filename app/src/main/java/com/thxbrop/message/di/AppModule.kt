package com.thxbrop.message.di

import androidx.room.Room
import com.thxbrop.message.Contracts
import com.thxbrop.message.application
import com.thxbrop.message.data.local.AppDatabase
import com.thxbrop.message.data.local.repository.MockAccountRepository
import com.thxbrop.message.data.local.repository.MockNotifyRepository
import com.thxbrop.message.data.local.storage.LocalStorage
import com.thxbrop.message.data.remote.AccountService
import com.thxbrop.message.data.remote.NotifyService
import com.thxbrop.message.domain.repository.AccountRepository
import com.thxbrop.message.domain.repository.AccountRepositoryImpl
import com.thxbrop.message.domain.repository.NotifyRepository
import com.thxbrop.message.domain.repository.NotifyRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    private fun retrofitClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request().newBuilder()
                    .addHeader("", "")
                    .build()
                it.withReadTimeout(5, TimeUnit.SECONDS)
                    .proceed(request)
            }.build()
    }

    @Provides
    @Singleton
    fun provideAccountService(): AccountService {
        return Retrofit.Builder()
            .baseUrl(Contracts.BASE_URL)
            .client(retrofitClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideNotifyService(): NotifyService {
        return Retrofit.Builder()
            .baseUrl(Contracts.BASE_URL)
            .client(retrofitClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideDatabase(): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            Contracts.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideStorage(): LocalStorage {
        return LocalStorage(
            notifyDao = provideDatabase().notifyDao(),
            userDao = provideDatabase().userDao()
        )
    }

    @Provides
    @Singleton
    fun provideAccountRepository(): AccountRepository {
        return if (Contracts.MOCK_ENABLE) MockAccountRepository()
        else AccountRepositoryImpl(
            accountService = provideAccountService(),
            localStorage = LocalStorage(
                notifyDao = provideDatabase().notifyDao(),
                userDao = provideDatabase().userDao()
            )
        )
    }

    @Provides
    @Singleton
    fun provideNotifyRepository(): NotifyRepository {
        return if (Contracts.MOCK_ENABLE) MockNotifyRepository()
        else NotifyRepositoryImpl(
            notifyService = provideNotifyService(),
            notifyDao = provideDatabase().notifyDao(),
            notifyDetailDao = provideDatabase().notifyDetailDao()
        )
    }
}
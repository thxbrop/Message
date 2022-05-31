package com.thxbrop.message.di

import androidx.room.Room
import com.thxbrop.message.Contracts
import com.thxbrop.message.application
import com.thxbrop.message.data.local.AppDatabase
import com.thxbrop.message.data.local.TokenManager
import com.thxbrop.message.data.local.repository.MockConversationRepository
import com.thxbrop.message.data.local.repository.MockUserRepository
import com.thxbrop.message.data.remote.ConversationService
import com.thxbrop.message.data.remote.UserService
import com.thxbrop.message.domain.repository.ConversationRepository
import com.thxbrop.message.domain.repository.ConversationRepositoryImpl
import com.thxbrop.message.domain.repository.UserRepository
import com.thxbrop.message.domain.repository.UserRepositoryImpl
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
        val builder = OkHttpClient.Builder()
        builder.addInterceptor { chain ->
            val request = chain.request().newBuilder()
            TokenManager.token?.let {
                request.addHeader("token", it)
                request.addHeader("userId", TokenManager.userId.toString())
            }
            chain.withReadTimeout(5, TimeUnit.SECONDS).proceed(request.build())
        }
        return builder.build()
    }


    @Provides
    @Singleton
    fun provideUserService(): UserService {
        return Retrofit.Builder()
            .baseUrl(Contracts.BASE_URL)
            .client(retrofitClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideConversationService(): ConversationService {
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
    fun provideConversationRepository(): ConversationRepository {
        return if (Contracts.MOCK_ENABLE) MockConversationRepository()
        else ConversationRepositoryImpl(
            conversationService = provideConversationService(),
            conversationDao = provideDatabase().conversationDao(),
        )
    }

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository {
        return if (Contracts.MOCK_ENABLE) MockUserRepository()
        else UserRepositoryImpl(
            userService = provideUserService(),
            userDao = provideDatabase().userDao()
        )
    }
}
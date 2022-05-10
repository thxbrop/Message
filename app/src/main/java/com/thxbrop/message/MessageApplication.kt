package com.thxbrop.message

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

val application get() = MessageApplication.instance

@HiltAndroidApp
class MessageApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: MessageApplication
    }
}
package com.thxbrop.message

import android.app.Application
import android.content.res.Resources
import dagger.hilt.android.HiltAndroidApp

val application get() = MessageApplication.instance
val resources: Resources get() = application.resources

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
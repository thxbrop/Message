package com.thxbrop.message

import android.app.Application
import android.content.res.Resources
import com.tencent.mmkv.MMKV
import dagger.hilt.android.HiltAndroidApp

val application get() = MessageApplication.instance
val resources: Resources get() = application.resources
val mmkv: MMKV get() = MMKV.defaultMMKV()

@HiltAndroidApp
class MessageApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        MMKV.initialize(this)
    }

    companion object {
        lateinit var instance: MessageApplication
    }
}
package com.androidproject

import android.app.Application
import com.androidproject.data.AppModule
import com.androidproject.data.DefaultAppModule

class MainApplication : Application() {
    lateinit var container: AppModule

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppModule(this.applicationContext)
    }
}

package com.androidproject

import android.app.Application
import com.androidproject.modules.AppModule
import com.androidproject.modules.DefaultAppModule

class MainApplication : Application() {
    lateinit var container: AppModule

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppModule(this.applicationContext)
    }
}

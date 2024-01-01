package com.androidproject

import android.app.Application
import com.androidproject.data.AppModule
import com.androidproject.data.DefaultAppModule

/**
 * The main application class
 * @property container the app container
 */
class MainApplication : Application() {
    lateinit var container: AppModule

    /**
     * Initializes the app container as a [DefaultAppModule]
     */
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppModule(this.applicationContext)
    }
}

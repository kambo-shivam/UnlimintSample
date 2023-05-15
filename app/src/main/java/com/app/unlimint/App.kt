package com.app.unlimint

import android.app.Application
import com.app.unlimint.di.module.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule))
        }
    }


    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}

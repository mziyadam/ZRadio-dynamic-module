package com.ziyad.zradio

import android.app.Application
import com.ziyad.core.di.databaseModule
import com.ziyad.core.di.networkModule
import com.ziyad.core.di.repositoryModule
import com.ziyad.zradio.di.useCaseModule
import com.ziyad.zradio.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            loadKoinModules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}
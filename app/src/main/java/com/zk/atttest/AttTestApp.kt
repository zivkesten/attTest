package com.zk.atttest

import android.app.Application
import com.zk.atttest.di.ViewModeslModule
import com.zk.atttest.networking.networkModule
import com.zk.atttest.repository.apiModule
import com.zk.atttest.repository.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AttTestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AttTestApp)
            modules(listOf(
				ViewModeslModule.modules,
                repositoryModule,
				apiModule,
                networkModule
            ))
        }
    }
}

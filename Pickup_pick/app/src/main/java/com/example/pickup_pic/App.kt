package com.example.pickup_pic

import android.app.Application
import android.content.res.Configuration
import android.os.StrictMode
import androidx.hilt.work.HiltWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App: Application() {

    //для хилта нужно аннотацию в 11 использовать создать этот класс
    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }


}
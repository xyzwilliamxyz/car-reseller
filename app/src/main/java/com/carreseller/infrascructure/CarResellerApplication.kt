package com.carreseller.infrascructure

import android.app.Application
import com.carreseller.infrascructure.di.apiModule
import com.carreseller.infrascructure.di.interactorModule
import com.carreseller.infrascructure.di.presenterModule
import org.koin.core.context.startKoin

class CarResellerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(apiModule, interactorModule, presenterModule)
        }
    }
}

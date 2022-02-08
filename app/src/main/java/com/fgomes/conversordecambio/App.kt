package com.fgomes.conversordecambio

import android.app.Application
import com.fgomes.conversordecambio.data.di.DataModules
import com.fgomes.conversordecambio.domain.di.DomainModule
import com.fgomes.conversordecambio.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application (){

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
        }

        DataModules.load()
        DomainModule.load()
        PresentationModule.load()
    }
}
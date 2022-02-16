package com.thibaultcharriere.leboncointest

import android.app.Application
import com.thibaultcharriere.leboncointest.core.di.KoinInitializer

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        KoinInitializer.init(this)
    }
}
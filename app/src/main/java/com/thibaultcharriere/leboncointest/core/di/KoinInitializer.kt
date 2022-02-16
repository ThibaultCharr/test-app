package com.thibaultcharriere.leboncointest.core.di

import android.content.Context
import com.thibaultcharriere.leboncointest.core.di.module.dataModule
import com.thibaultcharriere.leboncointest.core.di.module.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

object KoinInitializer {
    private val modules = listOf(
        apiModule,
        dataModule,
        domainModule,
        uiModule
    )

    fun init(context: Context) {
        startKoin {
            androidContext(context)
            modules(modules)
        }
    }
}
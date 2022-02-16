package com.thibaultcharriere.leboncointest.core.di.module

import com.thibaultcharriere.leboncointest.data.local.AppDatabase
import com.thibaultcharriere.leboncointest.data.repository.AlbumRepository
import org.koin.dsl.module

val dataModule = module {
    factory { AlbumRepository(get(), get()) }

    single { AppDatabase.create(get()) }

    single { get<AppDatabase>().albumDao() }
}
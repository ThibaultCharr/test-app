package com.thibaultcharriere.leboncointest.core.di

import com.thibaultcharriere.leboncointest.domain.albums.AlbumUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { AlbumUseCase(get()) }
}
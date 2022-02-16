package com.thibaultcharriere.leboncointest.core.di.module

import com.thibaultcharriere.leboncointest.ui.album.AlbumListViewmodel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { AlbumListViewmodel(get()) }
}
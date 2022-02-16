package com.thibaultcharriere.leboncointest.ui.album

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thibaultcharriere.leboncointest.domain.albums.Album
import com.thibaultcharriere.leboncointest.domain.albums.AlbumUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AlbumListViewmodel(private val useCase: AlbumUseCase): ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("AlbumListViewmodel", "Coroutine exception", throwable)
    }
    private val _albumsState = MutableStateFlow<AlbumsUiState>(AlbumsUiState.Empty)
    val albumsState: StateFlow<AlbumsUiState> = _albumsState


    fun fetchAlbums() {
        viewModelScope.launch(exceptionHandler + Dispatchers.IO) {
            _albumsState.value = AlbumsUiState.Loading
            useCase.getAlbums().collect { albums ->
                _albumsState.value = if (albums.isEmpty()) {
                    AlbumsUiState.Empty
                } else {
                    AlbumsUiState.Success(albums)
                }
                useCase.saveAlbums(albums)
            }
        }
    }
}

sealed class AlbumsUiState {
    data class Success(val albums: List<Album>): AlbumsUiState()
    object Empty: AlbumsUiState()
    object Loading: AlbumsUiState()
    // this could be used in future when the webservice returns smth else than a simple JSON
    data class Error(val exception: Throwable): AlbumsUiState()
}
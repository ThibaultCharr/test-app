package com.thibaultcharriere.leboncointest.domain.albums

import com.thibaultcharriere.leboncointest.data.repository.AlbumRepository
import kotlinx.coroutines.flow.Flow

class AlbumUseCase(private val repository: AlbumRepository) {

    fun getAlbums(): Flow<List<Album>> = repository.getAlbums()

    suspend fun saveAlbums(albums: List<Album>) {
        repository.insertOrReplaceAlbums(albums = albums)
    }
}
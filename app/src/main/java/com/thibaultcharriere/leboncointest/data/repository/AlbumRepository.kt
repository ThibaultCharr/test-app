package com.thibaultcharriere.leboncointest.data.repository

import android.util.Log
import com.thibaultcharriere.leboncointest.data.local.dao.AlbumDao
import com.thibaultcharriere.leboncointest.data.mapper.toAlbum
import com.thibaultcharriere.leboncointest.data.mapper.toAlbumModel
import com.thibaultcharriere.leboncointest.data.remote.api.AlbumApi
import com.thibaultcharriere.leboncointest.domain.albums.Album
import kotlinx.coroutines.flow.flow
import java.sql.SQLException

class AlbumRepository(
    private val remote: AlbumApi,
    private val local: AlbumDao
) {

    fun getAlbums() = flow {
        val localAlbums = local.getAlbums().map { it.toAlbum() }
        emit(localAlbums)

        try {
            val response = remote.getAlbums()
            if (response.isSuccessful && response.body() != null) {
                val remoteAlbums = response.body()!!.map { it.toAlbum() }
                emit(remoteAlbums)
            }
        } catch (exception: Exception) {
            Log.e(TAG, "There was an exception getting remote albums", exception)
        }
    }

    suspend fun insertOrReplaceAlbums(albums: List<Album>) {
        try {
            local.insertOrReplaceAlbums(*albums.map { it.toAlbumModel() }.toTypedArray())
        }  catch (exception: SQLException) {
            Log.e(TAG, "There was an SQL exception inserting or replacing albums", exception)
        }
    }

    companion object {
        const val TAG = "AlbumRepository"
    }
}
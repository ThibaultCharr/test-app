package com.thibaultcharriere.leboncointest.data.remote.api

import com.thibaultcharriere.leboncointest.data.remote.model.AlbumData
import retrofit2.Response
import retrofit2.http.GET

interface AlbumApi {

    @GET("/img/shared/technical-test.json")
    suspend fun getAlbums(): Response<List<AlbumData>>
}
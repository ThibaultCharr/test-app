package com.thibaultcharriere.leboncointest.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlbumData(
    val albumId: Long,
    val id: Long,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)
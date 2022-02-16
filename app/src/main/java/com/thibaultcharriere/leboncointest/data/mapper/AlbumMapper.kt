package com.thibaultcharriere.leboncointest.data.mapper

import com.thibaultcharriere.leboncointest.data.local.model.AlbumModel
import com.thibaultcharriere.leboncointest.data.remote.model.AlbumData
import com.thibaultcharriere.leboncointest.domain.albums.Album

fun AlbumData.toAlbum() = Album(
    id = id,
    albumId = albumId,
    title = title,
    url = url,
    thumbnailUrl = thumbnailUrl
)

fun AlbumModel.toAlbum() = Album(
    id = id,
    albumId = albumId,
    title = title,
    url = url,
    thumbnailUrl = thumbnailUrl
)

fun Album.toAlbumModel() = AlbumModel(
    id = id,
    albumId = albumId,
    title = title,
    url = url,
    thumbnailUrl = thumbnailUrl
)
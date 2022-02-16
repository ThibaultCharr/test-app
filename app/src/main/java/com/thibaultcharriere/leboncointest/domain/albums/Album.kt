package com.thibaultcharriere.leboncointest.domain.albums

data class Album(
    val albumId: Long,
    val id: Long,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)
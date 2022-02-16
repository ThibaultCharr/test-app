package com.thibaultcharriere.leboncointest.domain

import com.thibaultcharriere.leboncointest.data.local.model.AlbumModel
import com.thibaultcharriere.leboncointest.data.mapper.toAlbum
import com.thibaultcharriere.leboncointest.data.mapper.toAlbumModel
import com.thibaultcharriere.leboncointest.data.remote.model.AlbumData
import com.thibaultcharriere.leboncointest.domain.albums.Album
import org.junit.Assert.assertEquals
import org.junit.Test

class AlbumMapperTest {

    @Test
    fun `should map an api album to domain album`() {
        //Given
        val apiAlbum = AlbumData(
            albumId = 1,
            id = 1,
            title = "accusamus beatae ad facilis cum similique qui sunt",
            url = "https://via.placeholder.com/600/92c952",
            thumbnailUrl = "https://via.placeholder.com/150/92c952"
        )
        val domainAlbum = Album(
            albumId = 1,
            id = 1,
            title = "accusamus beatae ad facilis cum similique qui sunt",
            url = "https://via.placeholder.com/600/92c952",
            thumbnailUrl = "https://via.placeholder.com/150/92c952"
        )

        // When
        val album = apiAlbum.toAlbum()

        //Then
        assertEquals(album, domainAlbum)
    }

    @Test
    fun `should map a db album to domain album`() {
        //Given
        val dbAlbum = AlbumModel(
            albumId = 1,
            id = 1,
            title = "accusamus beatae ad facilis cum similique qui sunt",
            url = "https://via.placeholder.com/600/92c952",
            thumbnailUrl = "https://via.placeholder.com/150/92c952"
        )
        val domainAlbum = Album(
            albumId = 1,
            id = 1,
            title = "accusamus beatae ad facilis cum similique qui sunt",
            url = "https://via.placeholder.com/600/92c952",
            thumbnailUrl = "https://via.placeholder.com/150/92c952"
        )

        // When
        val album = dbAlbum.toAlbum()

        //Then
        assertEquals(album, domainAlbum)
    }

    @Test
    fun `should map a domain album to a db album`() {
        //Given
        val dbAlbum = AlbumModel(
            albumId = 1,
            id = 1,
            title = "accusamus beatae ad facilis cum similique qui sunt",
            url = "https://via.placeholder.com/600/92c952",
            thumbnailUrl = "https://via.placeholder.com/150/92c952"
        )
        val domainAlbum = Album(
            albumId = 1,
            id = 1,
            title = "accusamus beatae ad facilis cum similique qui sunt",
            url = "https://via.placeholder.com/600/92c952",
            thumbnailUrl = "https://via.placeholder.com/150/92c952"
        )

        // When
        val album = domainAlbum.toAlbumModel()

        //Then
        assertEquals(album, dbAlbum)
    }
}
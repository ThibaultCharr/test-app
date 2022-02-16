package com.thibaultcharriere.leboncointest.data

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.thibaultcharriere.leboncointest.data.local.dao.AlbumDao
import com.thibaultcharriere.leboncointest.data.local.model.AlbumModel
import com.thibaultcharriere.leboncointest.data.mapper.toAlbum
import com.thibaultcharriere.leboncointest.data.remote.api.AlbumApi
import com.thibaultcharriere.leboncointest.data.remote.model.AlbumData
import com.thibaultcharriere.leboncointest.data.repository.AlbumRepository
import com.thibaultcharriere.leboncointest.domain.albums.Album
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.io.File

class AlbumRepositoryTest {

    @Mock
    var albumApi: AlbumApi = Mockito.mock(AlbumApi::class.java)

    @Mock
    var dao: AlbumDao = Mockito.mock(AlbumDao::class.java)

    private lateinit var repository: AlbumRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        repository = AlbumRepository(albumApi, dao)
    }

    @Test
    fun `receiving an album list from the api should be emitted correctly`() = runBlocking {
        val json = File(javaClass.classLoader!!.getResource("albums.json").toURI()).readText()
        val albumData = Types.newParameterizedType(List::class.java, AlbumData::class.java)
        val jsonAdapter: JsonAdapter<List<AlbumData>> = Moshi.Builder().build().adapter(albumData)
        val response = Response.success(jsonAdapter.fromJson(json))


        val album1 = Album(
            albumId = 1,
            id = 1,
            title = "accusamus beatae ad facilis cum similique qui sunt",
            url = "https://via.placeholder.com/600/92c952",
            thumbnailUrl = "https://via.placeholder.com/150/92c952"
        )
        val album2 = Album(
            albumId = 1,
            id = 2,
            title = "reprehenderit est deserunt velit ipsam",
            url = "https://via.placeholder.com/600/771796",
            thumbnailUrl = "https://via.placeholder.com/150/771796"
        )
        val album3 = Album(
            albumId = 1,
            id = 3,
            title = "officia porro iure quia iusto qui ipsa ut modi",
            url = "https://via.placeholder.com/600/24f355",
            thumbnailUrl = "https://via.placeholder.com/150/24f355"
        )

        Mockito.`when`(albumApi.getAlbums()).thenReturn(response)
        Mockito.`when`(dao.getAlbums()).thenReturn(emptyList())
        val albums = mutableListOf<Album>()
        repository.getAlbums().collect { albums.addAll(it) }

        assertEquals(albums.size, 3)
        assertEquals(albums[0], album1)
        assertEquals(albums[1], album2)
        assertEquals(albums[2], album3)
    }

    @Test
    fun `should emit album list if api list is empty but database has albums`() = runBlocking {
        val json = File(javaClass.classLoader!!.getResource("emptyAlbums.json").toURI()).readText()
        val response = Response.error<List<AlbumData>>(
            400,
            json.toResponseBody("application/json".toMediaTypeOrNull())
        )

        val album1 = AlbumModel(
            albumId = 1,
            id = 1,
            title = "accusamus beatae ad facilis cum similique qui sunt",
            url = "https://via.placeholder.com/600/92c952",
            thumbnailUrl = "https://via.placeholder.com/150/92c952"
        )
        val album2 = AlbumModel(
            albumId = 1,
            id = 2,
            title = "reprehenderit est deserunt velit ipsam",
            url = "https://via.placeholder.com/600/771796",
            thumbnailUrl = "https://via.placeholder.com/150/771796"
        )
        val album3 = AlbumModel(
            albumId = 1,
            id = 3,
            title = "officia porro iure quia iusto qui ipsa ut modi",
            url = "https://via.placeholder.com/600/24f355",
            thumbnailUrl = "https://via.placeholder.com/150/24f355"
        )

        Mockito.`when`(albumApi.getAlbums()).thenReturn(response)
        Mockito.`when`(dao.getAlbums()).thenReturn(listOf(album1, album2, album3))
        val albums = mutableListOf<Album>()
        repository.getAlbums().collect { albums.addAll(it) }

        assertEquals(albums.size, 3)
        assertEquals(albums[0], album1.toAlbum())
        assertEquals(albums[1], album2.toAlbum())
        assertEquals(albums[2], album3.toAlbum())
    }

    @Test
    fun `should emit empty list if api list is empty and database is empty`() = runBlocking {
        val json = File(javaClass.classLoader!!.getResource("emptyAlbums.json").toURI()).readText()
        val response = Response.error<List<AlbumData>>(
            400,
            json.toResponseBody("application/json".toMediaTypeOrNull())
        )

        Mockito.`when`(albumApi.getAlbums()).thenReturn(response)
        Mockito.`when`(dao.getAlbums()).thenReturn(emptyList())
        val albums = mutableListOf<Album>()
        repository.getAlbums().collect { albums.addAll(it) }

        assertEquals(albums.size, 0)
    }
}
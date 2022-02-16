package com.thibaultcharriere.leboncointest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.thibaultcharriere.leboncointest.data.local.AppDatabase
import com.thibaultcharriere.leboncointest.data.local.model.AlbumModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class AlbumDaoTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertMultipleAlbumsThenFetchThem() = runBlocking {
        // Given
        val album1 = AlbumModel(id = 1, albumId = 1, title = "Test Album 1", url = "", thumbnailUrl = "")
        val album2 = AlbumModel(id = 2, albumId = 2, title = "Test Album 2", url = "", thumbnailUrl = "")

        // When
        database.albumDao().insertOrReplaceAlbums(album1, album2)

        val albums = database.albumDao().getAlbums()

        assertEquals(albums[0], album1)
        assertEquals(albums[1], album2)
    }

    @Test
    fun insertMultipleAlbumsThenReplaceOnehenFetchThem() = runBlocking {
        // Given
        val album1 = AlbumModel(id = 1, albumId = 1, title = "Test Album 1", url = "", thumbnailUrl = "")
        val album2 = AlbumModel(id = 2, albumId = 2, title = "Test Album 2", url = "", thumbnailUrl = "")
        val album3 = AlbumModel(id = 1, albumId = 2, title = "Test Album 2", url = "", thumbnailUrl = "")

        // When
        database.albumDao().insertOrReplaceAlbums(album1, album2)
        database.albumDao().insertOrReplaceAlbums(album3)

        val albums = database.albumDao().getAlbums()

        assertEquals(albums.size, 2)
        assertEquals(albums[0], album3)
        assertEquals(albums[1], album2)
    }
}
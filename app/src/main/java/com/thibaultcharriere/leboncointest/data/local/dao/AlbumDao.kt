package com.thibaultcharriere.leboncointest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thibaultcharriere.leboncointest.data.local.model.AlbumModel

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceAlbums(vararg albums: AlbumModel): List<Long>

    @Query("SELECT * FROM ${AlbumModel.TABLE_NAME}")
    suspend fun getAlbums(): List<AlbumModel>
}
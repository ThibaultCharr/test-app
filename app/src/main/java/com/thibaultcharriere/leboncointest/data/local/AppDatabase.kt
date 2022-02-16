package com.thibaultcharriere.leboncointest.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.thibaultcharriere.leboncointest.data.local.dao.AlbumDao
import com.thibaultcharriere.leboncointest.data.local.model.AlbumModel

@Database(
    entities = [
        AlbumModel::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun albumDao(): AlbumDao

    companion object {
        const val DATABASE_NAME = "leboncoin_db"

        fun create(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
    }
}
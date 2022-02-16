package com.thibaultcharriere.leboncointest.data.local.model

import android.icu.text.CaseMap
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.thibaultcharriere.leboncointest.data.local.model.AlbumModel.Companion.ID
import com.thibaultcharriere.leboncointest.data.local.model.AlbumModel.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME,
    primaryKeys = [ID]
)
data class AlbumModel(
    @ColumnInfo(name = ID) val id: Long,
    @ColumnInfo(name = ALBUM_ID) val albumId: Long,
    @ColumnInfo(name = TITLE) val title: String,
    @ColumnInfo(name = URL) val url: String,
    @ColumnInfo(name = THUMBNAIL_URL) val thumbnailUrl: String,
) {

    companion object {
        const val TABLE_NAME = "album"

        const val ID = "id"
        const val ALBUM_ID = "album_id"
        const val TITLE = "title"
        const val URL = "url"
        const val THUMBNAIL_URL = "thumbnail_url"
    }
}

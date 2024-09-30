package com.trenton.photogallery.feature.bookmark.data.model

import androidx.room.Entity
import com.trenton.photogallery.feature.bookmark.data.database.BOOKMARK_PHOTO_DATABASE

@Entity(tableName = BOOKMARK_PHOTO_DATABASE, primaryKeys = ["id", "apiSource"])
data class BookmarkedPhoto(
    val id: Int,
    val imageUrl: String,
    val title: String,
    val apiSource: String,
)

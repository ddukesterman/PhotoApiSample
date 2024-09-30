package com.trenton.photogallery.feature.bookmark.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.trenton.photogallery.feature.bookmark.data.model.BookmarkedPhoto

@Database(entities = [BookmarkedPhoto::class], version = 1)
abstract class BookmarkedPhotoDatabase: RoomDatabase() {
    abstract fun bookmarkedPhotoDao(): BookmarkPhotoDao
}
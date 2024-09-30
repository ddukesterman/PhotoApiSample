package com.trenton.photogallery.feature.bookmark.di

import android.content.Context
import androidx.room.Room
import com.trenton.photogallery.feature.bookmark.data.database.BOOKMARK_PHOTO_DATABASE
import com.trenton.photogallery.feature.bookmark.data.database.BookmarkPhotoDao
import com.trenton.photogallery.feature.bookmark.data.database.BookmarkedPhotoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BookmarkPhotoRoomModule {

    @Provides
    @Singleton
    fun provideBookmarkDatabase(@ApplicationContext context: Context): BookmarkedPhotoDatabase {
        return Room.databaseBuilder(
            context,
            BookmarkedPhotoDatabase::class.java,
            BOOKMARK_PHOTO_DATABASE,
        ).build()
    }

    @Provides
    fun providesPhotoDao(database: BookmarkedPhotoDatabase): BookmarkPhotoDao {
        return database.bookmarkedPhotoDao()
    }
}
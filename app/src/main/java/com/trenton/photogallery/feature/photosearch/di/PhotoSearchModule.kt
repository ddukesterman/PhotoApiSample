package com.trenton.photogallery.feature.photosearch.di

import com.trenton.photogallery.feature.photosearch.data.api.PexelPhotoApiService
import com.trenton.photogallery.feature.photosearch.data.api.PixabayPhotoApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PhotoSearchModule {

    @Provides
    @Singleton
    fun providePexelPhotoApiService(@Named("PexelsRetrofit") retrofit: Retrofit): PexelPhotoApiService =
        retrofit.create(PexelPhotoApiService::class.java)

    @Provides
    @Singleton
    fun providePixabayPhotoApiService(@Named("PixabayRetrofit") retrofit: Retrofit): PixabayPhotoApiService =
        retrofit.create(PixabayPhotoApiService::class.java)
}
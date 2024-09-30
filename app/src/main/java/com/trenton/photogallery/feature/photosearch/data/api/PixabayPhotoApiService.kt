package com.trenton.photogallery.feature.photosearch.data.api

import com.trenton.photogallery.feature.photosearch.data.model.PixabayPhotoSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface PixabayPhotoApiService {

    @GET("api")
    suspend fun searchForTerm(
        @Query("key") key: String,
        @Query("q") query: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Response<PixabayPhotoSearchResponse>
}
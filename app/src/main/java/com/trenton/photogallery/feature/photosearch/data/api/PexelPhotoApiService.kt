package com.trenton.photogallery.feature.photosearch.data.api

import com.trenton.photogallery.feature.photosearch.data.model.PexelPhotoSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PexelPhotoApiService {

    @GET("v1/search")
    suspend fun searchForTerm(
        @Query("query") keyword: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Response<PexelPhotoSearchResponse>
}
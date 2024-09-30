package com.trenton.photogallery.core.di

import com.trenton.photogallery.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @Named("PexelsRetrofit")
    fun providePexelsRetrofit(): Retrofit {
        val headerInterceptor = Interceptor { chain ->
            val request: Request = chain.request()
            val requestWithHeaders = request.newBuilder()
                .header("Authorization", BuildConfig.PEXEL_API_KEY)
                .build()
            chain.proceed(requestWithHeaders)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.pexels.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("PixabayRetrofit")
    fun providePixabayRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://pixabay.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
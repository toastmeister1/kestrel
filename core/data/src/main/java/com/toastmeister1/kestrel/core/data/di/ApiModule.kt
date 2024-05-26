package com.toastmeister1.kestrel.core.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.toastmeister1.kestrel.core.data.BuildConfig
import com.toastmeister1.kestrel.core.data.api.TMDBApi
import com.toastmeister1.kestrel.core.data.api.UnsplashApi
import com.toastmeister1.kestrel.core.data.interceptor.AuthenticationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient.Builder = OkHttpClient.Builder()

    @Provides
    @Singleton
    fun provideConverterFactory(json: Json): Converter.Factory {
        return json.asConverterFactory("application/json".toMediaType())
    }

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideTMDBApi(
        okHttpClient: OkHttpClient.Builder,
        converterFactory: Converter.Factory,
        authInterceptor: AuthenticationInterceptor,
    ): TMDBApi {
        val client = okHttpClient.addInterceptor(authInterceptor).build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.TMDB_BASE_URL)
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
            .create(TMDBApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUnsplashApi(
        okHttpClient: OkHttpClient.Builder,
        converterFactory: Converter.Factory,
        authInterceptor: AuthenticationInterceptor,
    ): UnsplashApi {
        val client = okHttpClient.addInterceptor(authInterceptor).build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.UNSPLASH_BASE_URL)
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
            .create(UnsplashApi::class.java)
    }
}

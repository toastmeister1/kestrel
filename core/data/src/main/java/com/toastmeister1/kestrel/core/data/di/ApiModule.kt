package com.toastmeister1.kestrel.core.data.di

import com.toastmeister1.kestrel.core.data.api.TMDBApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Provides
    @Singleton
    fun provideTMDBApi(@TMDBClient retrofit: Retrofit): TMDBApi = retrofit.create(TMDBApi::class.java)
}
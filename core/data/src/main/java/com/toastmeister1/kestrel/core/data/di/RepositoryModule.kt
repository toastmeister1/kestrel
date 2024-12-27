package com.toastmeister1.kestrel.core.data.di

import com.toastmeister1.kestrel.core.data.repository.MovieRepository
import com.toastmeister1.kestrel.core.data.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(movieRepository: MovieRepositoryImpl): MovieRepository
}
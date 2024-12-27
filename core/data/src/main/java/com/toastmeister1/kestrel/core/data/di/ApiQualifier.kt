package com.toastmeister1.kestrel.core.data.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class TMDBClient

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class UnsplashClient
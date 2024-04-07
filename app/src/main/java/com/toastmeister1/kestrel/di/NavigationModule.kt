package com.toastmeister1.kestrel.di

import com.toastmeister1.kestrel.core.navigation.AppComposeNavigator
import com.toastmeister1.kestrel.core.navigation.KestrelNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NavigationModule {

    @Binds
    @Singleton
    abstract fun provideAppComposeNavigator(
        navigator: KestrelNavigator
    ): AppComposeNavigator
}
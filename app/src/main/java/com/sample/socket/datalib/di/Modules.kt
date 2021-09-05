package com.sample.socket.datalib.di

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ExternalDependencies {

    @Singleton
    @Provides
    fun provideExternalDependencies(
        @ApplicationContext appContext: Context
    ) = EntryPointAccessors.fromApplication(
        appContext,
        DataLibraryExternalDependencies::class.java
    )
}

@Module
@InstallIn(SingletonComponent::class)
internal object GsonModule {

    @Singleton
    @Provides
    fun provideGsonInstance(): Gson = Gson()
}

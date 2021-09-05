package com.sample.socket.datalib.base.di

import com.sample.socket.datalib.base.WEB_SOCKET_URL
import com.sample.socket.datalib.di.WebSocketUrlQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GlobalDependenciesModule {
    @Singleton
    @Provides
    @WebSocketUrlQualifier
    fun provideSocketUrl() = WEB_SOCKET_URL
}
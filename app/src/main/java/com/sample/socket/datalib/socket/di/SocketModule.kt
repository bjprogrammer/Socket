package com.sample.socket.datalib.socket.di

import com.google.gson.Gson
import com.sample.socket.datalib.socket.SocketManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object SocketModule{

    @Singleton
    @Provides
    fun provideSocketManager(gson: Gson): SocketManager {
        return SocketManager(gson)
    }
}
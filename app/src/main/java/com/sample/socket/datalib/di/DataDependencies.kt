package com.sample.socket.datalib.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DataLibraryExternalDependencies {
    @WebSocketUrlQualifier
    fun getWebSocketUrl(): String
}
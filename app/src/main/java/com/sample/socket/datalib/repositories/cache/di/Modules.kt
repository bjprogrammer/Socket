package com.sample.socket.datalib.repositories.cache.di

import com.sample.socket.datalib.repositories.cache.AppCacheRepo
import com.sample.socket.datalib.repositories.cache.impl.AppCacheRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AppCacheModule {

    @Binds
    abstract fun bindAppCacheRepo(repo: AppCacheRepoImpl): AppCacheRepo
}
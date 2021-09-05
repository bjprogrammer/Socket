package com.sample.socket.datalib.dataSources.cache.di

import com.sample.socket.datalib.dataSources.cache.impl.UserCacheDataSourceImpl
import com.sample.socket.datalib.dataSources.cache.CacheDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class CacheModule {

    @UserCacheDataSourceQualifier
    @Binds
    abstract fun bindUserCacheDataSource(source: UserCacheDataSourceImpl): CacheDataSource
}


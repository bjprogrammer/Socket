package com.sample.socket.datalib.repositories.cache.impl

import com.sample.socket.datalib.common.getFromCacheNotNull
import com.sample.socket.datalib.common.saveInCache
import com.sample.socket.datalib.dataSources.cache.CacheDataSource
import com.sample.socket.datalib.dataSources.cache.di.UserCacheDataSourceQualifier
import com.sample.socket.datalib.models.result.Either
import com.sample.socket.datalib.models.result.Failure
import com.sample.socket.datalib.models.socket.SocketDataResponse
import com.sample.socket.datalib.repositories.cache.AppCacheRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class AppCacheRepoImpl @Inject constructor(
    @UserCacheDataSourceQualifier private val userCache: CacheDataSource
) : AppCacheRepo {
    override suspend fun saveLatestAQI(list: List<SocketDataResponse>)=
        saveInCache(list) { userCache.setData(list) }

    override suspend fun getAQIHistory() =
        getFromCacheNotNull { userCache.getData() }
}
package com.sample.socket.datalib.dataSources.cache

import com.sample.socket.datalib.models.socket.SocketDataResponse
import kotlinx.coroutines.flow.Flow

internal interface CacheDataSource {
    suspend fun setData(cityList: List<SocketDataResponse>): Boolean
    suspend fun getData(): List<SocketDataResponse>
}

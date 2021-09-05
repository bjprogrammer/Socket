package com.sample.socket.datalib.repositories.cache

import com.sample.socket.datalib.models.result.Either
import com.sample.socket.datalib.models.result.Failure
import com.sample.socket.datalib.models.socket.SocketDataResponse
import kotlinx.coroutines.flow.Flow

interface AppCacheRepo {
    suspend fun saveLatestAQI(list: List<SocketDataResponse>): Either<Boolean, Failure>
    suspend fun getAQIHistory(): Either<List<SocketDataResponse>, Failure>
}
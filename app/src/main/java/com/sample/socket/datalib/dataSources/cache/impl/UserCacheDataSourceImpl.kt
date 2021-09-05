package com.sample.socket.datalib.dataSources.cache.impl

import android.content.Context
import androidx.room.Room
import com.sample.socket.datalib.dataSources.cache.CacheDataSource
import com.sample.socket.datalib.database.AppDatabase
import com.sample.socket.datalib.models.socket.SocketDataResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

class UserCacheDataSourceImpl @Inject constructor(
    @ApplicationContext private val context : Context
) : CacheDataSource {

    @Singleton
    private fun provideRoom() = Room
        .databaseBuilder(context, AppDatabase::class.java, "air.db")
        .build()

    override suspend fun setData(cityList: List<SocketDataResponse>): Boolean {
        provideRoom().aqiDAO().insertAll(cityList)
        return true
    }

    override suspend fun getData(): List<SocketDataResponse> {
        return provideRoom().aqiDAO().getAllData()
    }
}

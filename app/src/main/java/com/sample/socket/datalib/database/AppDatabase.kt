package com.sample.socket.datalib.database

import androidx.room.Database
import com.sample.socket.datalib.models.socket.SocketDataResponse
import androidx.room.RoomDatabase

@Database(entities = [SocketDataResponse::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun aqiDAO(): AqiDao
}
package com.sample.socket.datalib.database

import androidx.room.OnConflictStrategy
import com.sample.socket.datalib.models.socket.SocketDataResponse
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AqiDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(socketDataResponseList: List<SocketDataResponse>)

    @Query("SELECT * FROM air_quality")
    abstract fun getAllData(): List<SocketDataResponse>
}
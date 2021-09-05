package com.sample.socket.datalib.models.cache

import com.sample.socket.datalib.base.AppData
import com.sample.socket.datalib.models.socket.SocketDataResponse

data class ListDataModel(val data: List<SocketDataResponse>) : AppData
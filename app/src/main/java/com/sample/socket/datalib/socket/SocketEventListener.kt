package com.sample.socket.datalib.socket

import com.sample.socket.datalib.models.socket.SocketDataResponse

interface SocketEventListener {
    fun onEventRecieved(data: List<SocketDataResponse>)
}
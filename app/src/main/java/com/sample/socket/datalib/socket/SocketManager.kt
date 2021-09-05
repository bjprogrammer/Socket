package com.sample.socket.datalib.socket

import android.util.Log
import com.google.gson.Gson
import com.sample.socket.datalib.models.socket.SocketDataResponse
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import javax.net.ssl.SSLSocketFactory
import com.google.gson.reflect.TypeToken

class SocketManager constructor(val gson: Gson){
    var mWebSocketClient: WebSocketClient? = null
    val type = object : TypeToken<List<SocketDataResponse>>(){}.type
    lateinit var listener: SocketEventListener

    fun setEventListener(socketEventListener: SocketEventListener){
        listener = socketEventListener
    }

    fun connectToSocket(uri: String) {
        mWebSocketClient = object : WebSocketClient(URI.create(uri)) {
            override fun onOpen(serverHandshake: ServerHandshake) {}

            override fun onMessage(message: String) {
                message.let {
                    val response:List<SocketDataResponse> = gson.fromJson(message, type)
                    listener.onEventRecieved(response)
                }
            }

            override fun onClose(i: Int, s: String, b: Boolean) {}
            override fun onError(e: Exception) {}
        }
        mWebSocketClient?.connect()
    }

    fun disconnect() {
        if (mWebSocketClient?.isOpen == true) {
            mWebSocketClient?.close()
        }
    }
}
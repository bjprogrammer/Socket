package com.sample.socket.datalib.useCases.socket

import com.sample.socket.datalib.base.CompletableUseCase
import com.sample.socket.datalib.di.DataLibraryExternalDependencies
import com.sample.socket.datalib.socket.SocketManager
import io.reactivex.Completable
import javax.inject.Inject

class ConnectSocketUseCase @Inject constructor(
        private val socketManager: SocketManager,
        private var externalDependencies: DataLibraryExternalDependencies
): CompletableUseCase{
    override fun execute(): Completable {
        return Completable.create{
            val uri = externalDependencies.getWebSocketUrl()
            socketManager.connectToSocket(uri)
        }
    }
}

class DisconnectSocketUseCase @Inject constructor(
    private val socketManager: SocketManager
): CompletableUseCase{
    override fun execute(): Completable {
        return Completable.create{
            socketManager.disconnect()
        }
    }
}
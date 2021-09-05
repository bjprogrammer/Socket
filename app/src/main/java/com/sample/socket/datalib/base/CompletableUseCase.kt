package com.sample.socket.datalib.base

import io.reactivex.Completable

interface CompletableUseCase {
    fun execute(): Completable
}
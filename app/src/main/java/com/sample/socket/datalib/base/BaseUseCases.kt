package com.sample.socket.datalib.base

abstract class BaseUseCase<out O : AppData> {
    abstract suspend fun process(): O?
}

abstract class BaseUseCaseUnWrapped<out O> {
    abstract suspend fun process(): O?
}

abstract class BaseUseCaseWithInput<in I : AppData, out O : AppData> {
    abstract suspend fun process(input: I): O?
}

abstract class BaseUseCaseUnWrappedWithInput<in I : AppData, out O> {
    abstract suspend fun process(input: I): O?
}
package com.sample.socket.datalib.common

import com.sample.socket.utils.tryThis
import com.sample.socket.datalib.models.result.Either
import com.sample.socket.datalib.models.result.Failure
import kotlinx.coroutines.flow.Flow
import java.io.IOException

internal inline fun saveInCache(
    input: Any?,
    method: (input: Any?) -> Boolean
): Either<Boolean, Failure> {
    return tryThis<Exception, Either<Boolean, Failure>>(
        { Either.Success(method(input)) },
        { exp -> Either.Error(Failure.Default(exp)) }
    ) ?: Either.Error(Failure.InvalidResult)
}

internal inline fun <R : Any> getFromCacheNotNull(
    method: () -> R
): Either<R, Failure> {
    return tryThis<IOException, Either<R, Failure>>(
        { Either.Success(method()) },
        { exp -> Either.Error(Failure.Default(exp)) }
    ) ?: Either.Error(Failure.InvalidResult)
}
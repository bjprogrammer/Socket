package com.sample.socket.base.helpers

import com.sample.socket.utils.tryThis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend inline fun <reified T> Flow<T>.listen(
    coScope: CoroutineScope,
    shareFlag: SharingStarted = SharingStarted.WhileSubscribed(),
    previousValuesCount: UInt = 1u,
    crossinline onResult: (res: T) -> Unit
) {
    catch { }
    shareIn(
        coScope,
        shareFlag,
        previousValuesCount.toInt()
    ).collect { data ->
        onResult(data)
    }
}

suspend inline fun <reified T> Flow<T>.listen(
    coScope: CoroutineScope,
    shareFlag: SharingStarted = SharingStarted.WhileSubscribed(),
    previousValuesCount: UInt = 1u,
    crossinline onResult: (res: T) -> Unit,
    crossinline onFailure: (e: Throwable) -> Unit
) {
    catch { exception -> onFailure(exception) }
    shareIn(
        coScope,
        shareFlag,
        previousValuesCount.toInt()
    ).collect { data ->
        onResult(data)
    }
}

suspend inline fun <reified T> Flow<T>.getValue(
    coScope: CoroutineScope,
    shareFlag: SharingStarted = SharingStarted.WhileSubscribed(),
    previousValuesCount: UInt = 1u,
): T? = tryThis<IllegalStateException, T> {
    suspendCoroutine {
        coScope.launch {
            flowOn(coScope.coroutineContext)
            shareIn(
                coScope,
                shareFlag,
                previousValuesCount.toInt()
            ).collect { data ->
                it.resume(data)
            }
        }
    }
}
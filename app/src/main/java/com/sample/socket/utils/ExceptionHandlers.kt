package com.sample.socket.utils

inline fun <reified E : Throwable, R> tryThis(onTry: () -> R, onCatch: (e: E) -> R): R? {
    try {
        return onTry()
    } catch (e: Exception) {
        if (e is E) {
            return onCatch(e)
        }else{
            return null
        }
    }
}

inline fun <reified E : Throwable> tryThis(onTry: () -> Unit, onCatch: () -> Unit) {
    try {
        onTry()
    } catch (e: Exception) {
        if (e is E) {
            onCatch()
        }
    }
}

inline fun <reified E : Throwable, R> tryThis(onTry: () -> R): R? {
    try {
        return onTry()
    } catch (e: Exception) {
        if (e is E) { }
    }

    return null
}

inline fun <reified E : Throwable> tryThis(onTry: () -> Unit) {
    try {
        onTry()
    } catch (e: Exception) {
        if (e is E) { }
    }
}
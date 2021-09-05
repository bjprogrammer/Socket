package com.sample.socket.datalib.models.result

import com.sample.socket.datalib.models.result.Either.Error
import com.sample.socket.datalib.models.result.Either.Success


/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Either] are either an instance of [Success] or [Error].
 * FP Convention dictates that [Success] is used for "failure"
 * and [Error] is used for "success".
 *
 * @see Success
 * @see Error
 */
sealed class Either<out SUCCESS, out ERROR> {
    /** * Represents the left side of [Either] class which by convention is a "Failure". */
    data class Success<out L>(val a: L) : Either<L, Nothing>()

    /** * Represents the right side of [Either] class which by convention is a "Success". */
    data class Error<out R>(val b: R) : Either<Nothing, R>()

    val isError get() = this is Error<ERROR>
    val isSuccess get() = this is Success<SUCCESS>

    fun <L> left(a: L) = Success(a)
    fun <R> right(b: R) = Error(b)

    fun either(fnL: (SUCCESS) -> Unit, fnR: (ERROR) -> Unit): Any =
        when (this) {
            is Success -> fnL(a)
            is Error -> fnR(b)
        }

    fun getSuccess() = if (this is Success) this.a else null
    fun getError() = if (this is Error) this.b else null
}

// Credits to Alex Hart -> https://proandroiddev.com/kotlins-nothing-type-946de7d464fb
// Composes 2 functions
fun <A, B, C> ((A) -> B).c(f: (B) -> C): (A) -> C = {
    f(this(it))
}

fun <T, L, R> Either<L, R>.flatMap(fn: (L) -> Either<T, R>): Either<T, R> =
    when (this) {
        is Either.Success -> fn(a)
        is Either.Error -> Either.Error(b)
    }

fun <T, L, R> Either<L, R>.map(fn: (L) -> (T)): Either<T, R> = this.flatMap(fn.c(::left))
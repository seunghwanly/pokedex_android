package io.seunghwanly.base


sealed interface Result<T, E>

open class Success<T>(val value: T) : Result<T, Nothing>

data class Failure<E>(val error: E) : Result<Nothing, E>


data class PageableSuccess<T>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<T>
) : Success<List<T>>(value = results)
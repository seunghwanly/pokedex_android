package io.seunghwanly.network.model

import kotlinx.serialization.Serializable

@Serializable
sealed class ApiResponse<out T> {
    @Serializable
    data class Success<T>(val data: T) : ApiResponse<T>()

    @Serializable
    data class Pageable<T>(
        val count: Int,
        val next: String?,
        val previous: String?,
        val results: List<T>,
    ) : ApiResponse<List<T>>()

    @Serializable
    data class Failure(val message: String, val code: Int? = null) : ApiResponse<Nothing>()
}



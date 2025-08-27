package com.maxi.newscleanarchitecture.common

sealed class SafeResult<out T> {

    data class Success<T>(val data: T) : SafeResult<T>()

    data class Error(
        val error: String,
        val throwable: Throwable? = null
    ) : SafeResult<Nothing>()
}

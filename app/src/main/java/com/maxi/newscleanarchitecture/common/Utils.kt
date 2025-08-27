package com.maxi.newscleanarchitecture.common

object Utils {

    fun <T> safeCall(block: () -> T): SafeResult<T> {
        return try {
            SafeResult.Success(block())
        } catch (e: Exception) {
            SafeResult.Error(e.message ?: "UnknownError $e")
        }
    }
}
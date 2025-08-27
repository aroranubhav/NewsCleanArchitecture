package com.maxi.newscleanarchitecture.data.common

sealed class Resource<out T> {

    data class Success<T>(val data: T) : Resource<T>()

    data class ApiError(val errorCode: Int, val errorMessage: String?) : Resource<Nothing>()

    data class DatabaseError(val errorMessage: String) : Resource<Nothing>()

    data object IOError : Resource<Nothing>()

    data object UnknownError : Resource<Nothing>()

    data object Loading : Resource<Nothing>()
}
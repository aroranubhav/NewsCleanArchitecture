package com.maxi.newscleanarchitecture.presentation.base

sealed class UiState<out T> {

    data class Success<T>(val data: T) : UiState<T>()

    data class ApiError(val errorMessage: String) : UiState<Nothing>()

    data class DatabaseError(val errorMessage: String) : UiState<Nothing>()

    data object IOError : UiState<Nothing>()

    data object UnknownError : UiState<Nothing>()

    data object Loading : UiState<Nothing>()
}
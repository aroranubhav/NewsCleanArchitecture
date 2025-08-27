package com.maxi.newscleanarchitecture.data.common

import android.database.sqlite.SQLiteException
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
    return try {
        val response = apiCall()
        Resource.Success(response)
    } catch (e: ApiException) {
        Resource.ApiError(e.errorCode, e.errorMessage)
    } catch (e: IOException) {
        Resource.IOError
    } catch (e: Exception) {
        Resource.UnknownError
    }
}

suspend fun <T> safeDbCall(dbCall: suspend () -> T): Resource<T> {
    return try {
        val response = dbCall()
        Resource.Success(response)
    } catch (e: SQLiteException) {
        Resource.DatabaseError(e.message ?: "A database exception occurred!")
    } catch (e: Exception) {
        Resource.UnknownError
    }
}

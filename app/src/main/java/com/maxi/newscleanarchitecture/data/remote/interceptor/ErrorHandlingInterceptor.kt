package com.maxi.newscleanarchitecture.data.remote.interceptor

import com.maxi.newscleanarchitecture.data.common.ApiException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ErrorHandlingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val response: Response

        try {
            response = chain.proceed(request)
        } catch (e: IOException) {
            throw IOException("Network Error ${e.message}")
        }

        if (!response.isSuccessful) {
            val errorBody = response.body?.string()
            throw ApiException(response.code, errorBody)
        }

        return response
    }
}
package com.maxi.newscleanarchitecture.data.remote.interceptor

import com.maxi.newscleanarchitecture.data.remote.utils.ApiConstants.Headers.API_KEY
import com.maxi.newscleanarchitecture.data.remote.utils.ApiConstants.Headers.USER_AGENT
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(
    private val apiKey: String,
    private val userAgent: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .addHeader(API_KEY, apiKey)
                .addHeader(USER_AGENT, userAgent)
                .build()
        )
    }
}
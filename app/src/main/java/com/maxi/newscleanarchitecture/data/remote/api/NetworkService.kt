package com.maxi.newscleanarchitecture.data.remote.api

import com.maxi.newscleanarchitecture.data.remote.model.NewsResponseDto
import com.maxi.newscleanarchitecture.data.remote.utils.ApiConstants.EndPoints.TOP_HEADLINES
import com.maxi.newscleanarchitecture.data.remote.utils.ApiConstants.QueryParams.COUNTRY
import com.maxi.newscleanarchitecture.data.remote.utils.ApiConstants.QueryParams.DEFAULT_COUNTRY
import com.maxi.newscleanarchitecture.data.remote.utils.ApiConstants.QueryParams.DEFAULT_LANGUAGE
import com.maxi.newscleanarchitecture.data.remote.utils.ApiConstants.QueryParams.LANGUAGE
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET(TOP_HEADLINES)
    suspend fun getNews(
        @Query(LANGUAGE) language: String = DEFAULT_LANGUAGE,
        @Query(COUNTRY) country: String = DEFAULT_COUNTRY
    ): NewsResponseDto
}
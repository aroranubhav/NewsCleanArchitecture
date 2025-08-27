package com.maxi.newscleanarchitecture.data.remote.model

import com.google.gson.annotations.SerializedName
import com.maxi.newscleanarchitecture.data.common.DataConstants.ARTICLES
import com.maxi.newscleanarchitecture.data.common.DataConstants.STATUS
import com.maxi.newscleanarchitecture.data.common.DataConstants.TOTAL_RESULTS

data class NewsResponseDto(
    @SerializedName(STATUS)
    val status: String = "",
    @SerializedName(TOTAL_RESULTS)
    val totalResults: Int = 0,
    @SerializedName(ARTICLES)
    val articles: List<ArticleDto> = arrayListOf()
)

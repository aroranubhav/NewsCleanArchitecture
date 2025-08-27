package com.maxi.newscleanarchitecture.data.remote.model

import com.google.gson.annotations.SerializedName
import com.maxi.newscleanarchitecture.common.SafeResult
import com.maxi.newscleanarchitecture.data.common.DataConstants.DESCRIPTION
import com.maxi.newscleanarchitecture.data.common.DataConstants.IMAGE_URL
import com.maxi.newscleanarchitecture.data.common.DataConstants.PUBLISHED_AT
import com.maxi.newscleanarchitecture.data.common.DataConstants.TITLE
import com.maxi.newscleanarchitecture.data.common.DataConstants.URL
import com.maxi.newscleanarchitecture.data.common.Utils
import com.maxi.newscleanarchitecture.data.local.entity.ArticleEntity

data class ArticleDto(
    @SerializedName(TITLE)
    val title: String = "",
    @SerializedName(DESCRIPTION)
    val description: String? = "",
    @SerializedName(URL)
    val url: String = "",
    @SerializedName(IMAGE_URL)
    val imageUrl: String? = "",
    @SerializedName(PUBLISHED_AT)
    val publishedAt: String? = ""
)

fun ArticleDto.toEntity(): ArticleEntity {
    val publishedDateInstant = Utils.stringDateToInstant(publishedAt)
    val publishedDate = if (publishedDateInstant is SafeResult.Success) {
        publishedDateInstant.data
    } else {
        null
    }
    return ArticleEntity(
        title, description ?: "", url, imageUrl ?: "", publishedDate
    )
}

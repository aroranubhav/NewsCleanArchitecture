package com.maxi.newscleanarchitecture.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maxi.newscleanarchitecture.data.common.DataConstants.DESCRIPTION
import com.maxi.newscleanarchitecture.data.common.DataConstants.IMAGE_URL
import com.maxi.newscleanarchitecture.data.common.DataConstants.PUBLISHED_AT
import com.maxi.newscleanarchitecture.data.common.DataConstants.TITLE
import com.maxi.newscleanarchitecture.data.common.DataConstants.URL
import com.maxi.newscleanarchitecture.data.local.utils.LocalConstants.TABLES.ARTICLES
import com.maxi.newscleanarchitecture.domain.model.Article
import java.time.Instant

@Entity(tableName = ARTICLES)
data class ArticleEntity(
    @ColumnInfo(name = TITLE)
    val title: String,
    @ColumnInfo(name = DESCRIPTION)
    val description: String,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = URL)
    val url: String,
    @ColumnInfo(name = IMAGE_URL)
    val imageUrl: String,
    @ColumnInfo(name = PUBLISHED_AT)
    val publishedAt: Instant?
)

fun ArticleEntity.toDomain(): Article =
    Article(title, description, url, imageUrl)

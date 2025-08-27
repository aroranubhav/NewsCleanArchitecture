package com.maxi.newscleanarchitecture.data.common

import com.maxi.newscleanarchitecture.data.local.entity.ArticleEntity
import com.maxi.newscleanarchitecture.data.local.entity.toDomain
import com.maxi.newscleanarchitecture.data.remote.model.ArticleDto
import com.maxi.newscleanarchitecture.data.remote.model.toEntity
import com.maxi.newscleanarchitecture.domain.model.Article

fun List<ArticleDto>.toEntityList(): List<ArticleEntity> = map {it.toEntity()}
fun List<ArticleEntity>.toDomainList(): List<Article> = map { it.toDomain() }
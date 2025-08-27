package com.maxi.newscleanarchitecture.domain.repository

import com.maxi.newscleanarchitecture.data.common.Resource
import com.maxi.newscleanarchitecture.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNews(): Flow<Resource<List<Article>>>

    suspend fun refreshNews(): Resource<Unit>
}
package com.maxi.newscleanarchitecture.domain.usecase.news

import com.maxi.newscleanarchitecture.data.common.Resource
import com.maxi.newscleanarchitecture.domain.model.Article
import com.maxi.newscleanarchitecture.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) : NewsUseCase {

    override fun getNews(): Flow<Resource<List<Article>>> =
        repository.getNews()

    override suspend fun refreshNews(): Resource<Unit> {
        return repository.refreshNews()
    }
}
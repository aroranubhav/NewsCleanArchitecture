package com.maxi.newscleanarchitecture.domain.usecase.refreshnews

import com.maxi.newscleanarchitecture.data.common.Resource
import com.maxi.newscleanarchitecture.domain.repository.NewsRepository
import javax.inject.Inject

class DefaultRefreshNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) : RefreshNewsUseCase {

    override suspend fun refreshNews(): Resource<Unit> =
        repository.refreshNews()
}
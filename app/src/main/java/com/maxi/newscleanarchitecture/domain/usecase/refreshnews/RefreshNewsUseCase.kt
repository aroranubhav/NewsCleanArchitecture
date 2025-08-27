package com.maxi.newscleanarchitecture.domain.usecase.refreshnews

import com.maxi.newscleanarchitecture.data.common.Resource

interface RefreshNewsUseCase {

    suspend fun refreshNews(): Resource<Unit>
}
package com.maxi.newscleanarchitecture.di.module

import com.maxi.newscleanarchitecture.data.local.dao.NewsDao
import com.maxi.newscleanarchitecture.data.remote.api.NetworkService
import com.maxi.newscleanarchitecture.data.repository.DefaultNewsRepository
import com.maxi.newscleanarchitecture.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNewsRepository(
        networkService: NetworkService,
        dao: NewsDao
    ): NewsRepository =
        DefaultNewsRepository(networkService, dao)

}
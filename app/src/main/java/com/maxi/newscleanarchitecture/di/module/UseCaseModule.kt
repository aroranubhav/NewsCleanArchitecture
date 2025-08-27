package com.maxi.newscleanarchitecture.di.module

import com.maxi.newscleanarchitecture.domain.repository.NewsRepository
import com.maxi.newscleanarchitecture.domain.usecase.news.DefaultNewsUseCase
import com.maxi.newscleanarchitecture.domain.usecase.news.NewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideUseCase(
        repository: NewsRepository
    ): NewsUseCase =
        DefaultNewsUseCase(repository)
}
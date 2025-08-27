package com.maxi.newscleanarchitecture.di.module

import com.maxi.newscleanarchitecture.presentation.news.adapter.NewsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@InstallIn(ActivityComponent::class)
@Module
object NewsModule {

    @Provides
    @ActivityScoped
    fun provideNewsAdapter(): NewsAdapter =
        NewsAdapter(arrayListOf())
}
package com.maxi.newscleanarchitecture.di.module

import android.content.Context
import androidx.room.Room
import com.maxi.newscleanarchitecture.BuildConfig
import com.maxi.newscleanarchitecture.common.DefaultDispatcherProvider
import com.maxi.newscleanarchitecture.common.DefaultNetworkConnectivityHelper
import com.maxi.newscleanarchitecture.common.DispatcherProvider
import com.maxi.newscleanarchitecture.common.FirstLaunchManager
import com.maxi.newscleanarchitecture.common.NetworkConnectivityHelper
import com.maxi.newscleanarchitecture.data.local.NewsDatabase
import com.maxi.newscleanarchitecture.data.local.dao.NewsDao
import com.maxi.newscleanarchitecture.data.local.utils.LocalConstants.NEWS_DB
import com.maxi.newscleanarchitecture.data.remote.api.NetworkService
import com.maxi.newscleanarchitecture.data.remote.interceptor.AuthorizationInterceptor
import com.maxi.newscleanarchitecture.data.remote.interceptor.ErrorHandlingInterceptor
import com.maxi.newscleanarchitecture.data.remote.utils.ApiConstants.BaseUrls.BASE_URL
import com.maxi.newscleanarchitecture.data.remote.utils.ApiConstants.Headers.USER_AGENT
import com.maxi.newscleanarchitecture.di.qualifier.BaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String =
        BASE_URL

    @Provides
    @Singleton
    fun provideAuthorizationInterceptor(): AuthorizationInterceptor =
        AuthorizationInterceptor(
            BuildConfig.API_KEY,
            USER_AGENT
        )

    @Provides
    @Singleton
    fun provideErrorHandlingInterceptor(): ErrorHandlingInterceptor =
        ErrorHandlingInterceptor()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

    @Provides
    @Singleton
    fun provideHttpClient(
        authorizationInterceptor: AuthorizationInterceptor,
        errorHandlingInterceptor: ErrorHandlingInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient().newBuilder()
            .addInterceptor(authorizationInterceptor)
            .addInterceptor(errorHandlingInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(
        @BaseUrl baseUrl: String,
        httpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(converterFactory)
            .build()

    @Provides
    fun provideNetworkService(
        retrofit: Retrofit
    ): NetworkService =
        retrofit.create(NetworkService::class.java)

    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): NewsDatabase =
        Room
            .databaseBuilder(
                context,
                NewsDatabase::class.java,
                NEWS_DB
            ).build()

    @Provides
    fun provideNewsDao(
        database: NewsDatabase
    ): NewsDao =
        database.newDao()

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider =
        DefaultDispatcherProvider()

    @Provides
    @Singleton
    fun provideNetworkConnectivityHelper(
        @ApplicationContext context: Context
    ): NetworkConnectivityHelper =
        DefaultNetworkConnectivityHelper(context)

    @Provides
    @Singleton
    fun provideFirstLaunchManager(
        @ApplicationContext context: Context
    ): FirstLaunchManager =
        FirstLaunchManager(context)

}
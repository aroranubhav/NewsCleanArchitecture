package com.maxi.newscleanarchitecture.presentation.news.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxi.newscleanarchitecture.common.Constants.NETWORK_ERROR
import com.maxi.newscleanarchitecture.common.Constants.UNKNOWN_ERROR
import com.maxi.newscleanarchitecture.common.DispatcherProvider
import com.maxi.newscleanarchitecture.common.FirstLaunchManager
import com.maxi.newscleanarchitecture.common.NetworkConnectivityHelper
import com.maxi.newscleanarchitecture.data.common.Resource
import com.maxi.newscleanarchitecture.domain.model.Article
import com.maxi.newscleanarchitecture.domain.usecase.news.NewsUseCase
import com.maxi.newscleanarchitecture.presentation.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val useCase: NewsUseCase,
    private val firstLaunchManager: FirstLaunchManager,
    private val dispatcherProvider: DispatcherProvider,
    private val networkConnectivityHelper: NetworkConnectivityHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    init {
        performFirstLaunchNewsRefresh()
    }

    private fun performFirstLaunchNewsRefresh() {
        viewModelScope.launch(dispatcherProvider.io) {
            val isAlreadyLaunched = firstLaunchManager.checkFirstFetchDone()
            if (!isAlreadyLaunched) {
                if (networkConnectivityHelper.hasNetworkConnectivity()) {
                    val response = refreshNews()
                    if (response is Resource.Success) {
                        getNews()
                        firstLaunchManager.markFirstFetchDone()
                    } else {
                        _uiState.value = UiState.ApiError(UNKNOWN_ERROR)
                        Log.e(NewsViewModelTAG, "performFirstLaunchNewsRefresh: failed $response")
                    }
                } else {
                    _uiState.value = UiState.IOError
                }
            } else {
                getNews()
            }
        }
    }

    suspend fun refreshNews(): Resource<Unit> {
        Log.d(NewsViewModelTAG, "new refreshed")
        _isRefreshing.value = true
        val deferred = viewModelScope.async(dispatcherProvider.io) {
            useCase.refreshNews()
        }
        val deferredResult = deferred.await()
        _isRefreshing.value = false
        return deferredResult
    }

    private fun getNews() {
        viewModelScope.launch {
            useCase.getNews()
                .flowOn(dispatcherProvider.io)
                .collect { response ->
                    when (response) {
                        is Resource.Success -> {
                            _uiState.value = UiState.Success(response.data)
                        }

                        is Resource.ApiError -> {
                            _uiState.value = UiState.ApiError(
                                response.errorMessage
                            )
                        }

                        is Resource.DatabaseError -> {
                            _uiState.value = UiState.DatabaseError(response.errorMessage)
                        }

                        is Resource.IOError -> {
                            _uiState.value = UiState.IOError
                        }

                        is Resource.UnknownError -> {
                            _uiState.value = UiState.UnknownError
                        }

                        is Resource.Loading -> {
                            _uiState.value = UiState.Loading
                        }
                    }
                }
        }
    }
}

const val NewsViewModelTAG = "NewsViewModel"
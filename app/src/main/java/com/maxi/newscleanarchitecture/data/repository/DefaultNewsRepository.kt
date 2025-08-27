package com.maxi.newscleanarchitecture.data.repository

import android.util.Log
import com.maxi.newscleanarchitecture.data.common.Resource
import com.maxi.newscleanarchitecture.data.common.safeApiCall
import com.maxi.newscleanarchitecture.data.common.safeDbCall
import com.maxi.newscleanarchitecture.data.common.toDomainList
import com.maxi.newscleanarchitecture.data.common.toEntityList
import com.maxi.newscleanarchitecture.data.local.dao.NewsDao
import com.maxi.newscleanarchitecture.data.remote.api.NetworkService
import com.maxi.newscleanarchitecture.domain.model.Article
import com.maxi.newscleanarchitecture.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultNewsRepository @Inject constructor(
    private val networkService: NetworkService,
    private val dao: NewsDao
) : NewsRepository {

    override fun getNews(): Flow<Resource<List<Article>>> = flow {
        emit(Resource.Loading)
        emitAll(
            dao.getNews().map {
                Resource.Success(it.toDomainList())
            }
        )
    }

    override suspend fun refreshNews(): Resource<Unit> {
        return when (val apiResponse = safeApiCall { networkService.getNews() }) {
            is Resource.Success -> {
                val dto = apiResponse.data.articles
                val entities = dto.toEntityList()

                val insertResult: Resource<List<Long>> = safeDbCall {
                    dao.insertNews(entities)
                }

                Log.d(DefaultNewsRepositoryTAG, "refreshNews: $insertResult")
                //TODO: check insertion success & failures return types -- useful for logging!

                when (insertResult) {
                    is Resource.Success -> {
                        Resource.Success(Unit)
                    }

                    is Resource.DatabaseError -> {
                        Resource.DatabaseError(insertResult.errorMessage)
                    }

                    else -> {
                        Resource.UnknownError
                    }
                }
            }

            is Resource.ApiError -> apiResponse
            is Resource.IOError -> apiResponse
            is Resource.UnknownError -> apiResponse
            else -> Resource.UnknownError
        }
    }

}

const val DefaultNewsRepositoryTAG = "DefaultNewsRepository"
/**
 * 🔄
 *
 * UI starts collecting getNews().
 *
 * Immediately gets Resource.Loading.
 *
 * Then whatever is currently in the DB as Resource.Success(...).
 *
 * Later, when syncNews() runs and inserts fresh data:
 *
 * Room notifies all flows from dao.getNews().
 *
 * Repository automatically emits a new Resource.Success(freshList) to the UI.
 *
 * So the DB emit happens automatically thanks to Room + Flow. You don’t manually trigger it —
 * insertion into the DB triggers invalidation, Room re-queries, Flow emits, and your emitAll passes it up.
 */
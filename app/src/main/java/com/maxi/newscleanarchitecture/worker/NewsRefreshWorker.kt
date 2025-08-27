package com.maxi.newscleanarchitecture.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.maxi.newscleanarchitecture.domain.usecase.refreshnews.RefreshNewsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NewsRefreshWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val useCase: RefreshNewsUseCase
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return try {
            useCase.refreshNews()
            Result.success()
        } catch (e: Exception) {
            Log.e(NewsRefreshWorkerTAG, "doWork: ${e.message}")
            Result.retry()
        }
    }
}

const val NewsRefreshWorkerTAG = "NewsRefreshWorker"
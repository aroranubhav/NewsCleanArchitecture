package com.maxi.newscleanarchitecture.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.maxi.newscleanarchitecture.domain.usecase.refreshnews.RefreshNewsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.io.IOException

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
        } catch (e: IOException) {
            Log.e(NewsRefreshWorkerTAG, "Error while refreshing news, $e")
            Result.retry()
        } catch (e: Exception) {
            Log.e(NewsRefreshWorkerTAG, "Error while refreshing news, $e")
            Result.failure(
                workDataOf("error" to e.localizedMessage)
            )
        }
    }
}

const val NewsRefreshWorkerTAG = "NewsRefreshWorker"
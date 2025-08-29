package com.maxi.newscleanarchitecture.worker

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.concurrent.TimeUnit

object NewsRefreshScheduler {

    /**
     * WorkManager is OS-managed — runs via JobScheduler / AlarmManager / FirebaseJobDispatcher
     */
    private const val UNIQUE_WORK_NAME = "DailyNewsSyncWorker"

    fun scheduleNewsRefresh(workManager: WorkManager) {
        val workRequest = PeriodicWorkRequestBuilder<NewsRefreshWorker>(
            3,
            TimeUnit.HOURS,
            PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS,
            TimeUnit.MILLISECONDS
        ).setInitialDelay(
            3,
            TimeUnit.HOURS
        ).setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        ).setBackoffCriteria(
            BackoffPolicy.EXPONENTIAL,
            WorkRequest.MIN_BACKOFF_MILLIS,
            TimeUnit.MILLISECONDS
        )
            .build()

        workManager.enqueueUniquePeriodicWork(
            UNIQUE_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    /** logic to run scheduler once a day at 7 AM

    val now = LocalDateTime.now()

    val scheduledTime = now.toLocalDate()
    .plusDays(1)
    .atTime(7, 0)

    val timeDifference = Duration.between(now, scheduledTime)
     **/
}
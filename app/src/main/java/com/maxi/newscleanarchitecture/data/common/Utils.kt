package com.maxi.newscleanarchitecture.data.common

import com.maxi.newscleanarchitecture.common.SafeResult
import com.maxi.newscleanarchitecture.common.Utils.safeCall
import java.time.Instant

object Utils {

    fun stringDateToInstant(date: String?): SafeResult<Instant> =
        if (date.isNullOrEmpty()) SafeResult.Error(
            "Date string is null or empty!"
        )
        else safeCall { Instant.parse(date) }
}
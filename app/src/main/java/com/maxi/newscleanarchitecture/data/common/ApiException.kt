package com.maxi.newscleanarchitecture.data.common

import java.io.IOException

/**
 * ApiException makes it clear that “this error came from an API call, not just a random I/O issue”
 */
data class ApiException(
    val errorCode: Int,
    val errorMessage: String?,
    val errorBody: String? = null,
    val requestUrl: String? = null,
    val method: String? = null
) : IOException() {

    /**
     * overriding the message property from Throwable(the parent of IOException) --
     * to return our own dynamic string instead of whatever was passed to the constructor.
     */

    override val message: String
        get() = buildString {
            append("HTTP Exception $errorCode")
            if (!errorMessage.isNullOrBlank()) {
                append(": $errorMessage")
            }
            if (!requestUrl.isNullOrBlank()) {
                append(" (URL = $requestUrl)")
            }
        }
}

package com.maxi.newscleanarchitecture.common

import android.content.Context
import android.net.ConnectivityManager

interface NetworkConnectivityHelper {

    fun hasNetworkConnectivity(): Boolean
}

class DefaultNetworkConnectivityHelper(
    private val context: Context
) : NetworkConnectivityHelper {

    override fun hasNetworkConnectivity(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected ?: false
    }

}
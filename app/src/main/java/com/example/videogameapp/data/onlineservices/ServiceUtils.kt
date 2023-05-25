package com.example.videogameapp.data.onlineservices

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object ServiceUtils {
    const val API_KEY = "d2e12992a64747b88b2cc6e059639efc"

    const val ORDER_POPULAR = "-games_count"
    const val SPINNER_PAGE = 1
    const val SPINNER_SIZE = 8

    const val GENRES = "genres"
    const val PLATFORMS = "platforms"

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}
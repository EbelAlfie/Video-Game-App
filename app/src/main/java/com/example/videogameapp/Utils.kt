package com.example.videogameapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object Utils {
    const val USER_OFFLINE = 0
    const val USER_ONLINE = 1

    const val ID_KEY = "id"

    fun setUpAlertDialog(title: String, message: String, context: Context): AlertDialog.Builder {
        return AlertDialog.Builder(context).apply {
            setTitle(title)
            setMessage(message)
        }
    }

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

    fun generateIntent(context: Context, id: Int?, slug: String?) {

    }
}
package com.example.videogameapp

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.text.Html
import android.text.Spanned
import android.view.View
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object Utils {

    const val ID_KEY = "id"
    const val OBJ_KEY = "Object"

    const val MODE_SUB_PAGE = 5
    const val MODE_ALL_PAGE = 4

    /*const val DELAY_TIME: Long = 2500
    const val PERIODE_TIME: Long = 8000*/

    fun createLoading(context: Context): AlertDialog.Builder {
        return AlertDialog.Builder(context).apply {
            setCancelable(false)
            setView(View.inflate(context, R.layout.loading_layout, null))
        }
    }

    fun createLoadingImage(context: Context): CircularProgressDrawable {
        val loading = CircularProgressDrawable(context)
        loading.strokeWidth = 5f
        loading.centerRadius = 30f
        loading.setColorSchemeColors(context.getColor(R.color.white))
        loading.start()
        return loading
    }

    fun setUpAlertDialog(title: String, message: String, context: Context): AlertDialog.Builder {
        return AlertDialog.Builder(context).apply {
            setTitle(title)
            setMessage(message)
            setCancelable(false)
        }
    }

    fun checkNetwork(context: Context) = isNetworkAvailable(context)

    fun String.fromHtml(): Spanned {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    }

    private fun isNetworkAvailable(context: Context): Boolean {
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

    fun generateIntent(context: Context, id: Long?, kelas: Class<*>): Intent {
        val intent = Intent(context, kelas)
        intent.putExtra(ID_KEY, id)
        return intent
    }
}

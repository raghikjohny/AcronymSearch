package com.raghi.acronymsearch.network

import android.content.Context
import android.net.ConnectivityManager

class NetworkUtil {
    companion object {
        fun checkConnectionType(context: Context): Boolean {
            var isOnline: Boolean
            val connection =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connection.activeNetworkInfo
            isOnline = activeNetwork != null
            return isOnline
        }
    }
}
package com.ayushsinghal.bhagavadgita.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.ayushsinghal.bhagavadgita.BhagvadGitaApp

//class NetworkUtils {
//    companion object {
//
//        fun isInternetAvailable(): Boolean {
//            return try {
//                val ipAddr = InetAddress.getByName("google.com")
//                //You can replace it with your name
//                return !ipAddr.equals("")
//            } catch (e: Exception) {
//                false
//            }
//        }
//    }
//}

//sealed class ConnectionState {
//    object Available : ConnectionState()
//    object Unavailable : ConnectionState()
//}
//
//val Context.currentConnectivityState: ConnectionState
//    get() {
//        val connectivityManager =
//            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        return getCurrentConnectivityState(connectivityManager)
//    }
//
//private fun getCurrentConnectivityState(
//    connectivityManager: ConnectivityManager
//): ConnectionState {
//    val connected = connectivityManager.allNetworks.any { network ->
//        connectivityManager.getNetworkCapabilities(network)
//            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
//            ?: false
//    }
//
//    return if (connected) ConnectionState.Available else ConnectionState.Unavailable
//}

object NetworkUtils {
    fun isInternetAvailable(bhagvadGitaApp: BhagvadGitaApp): Boolean {
        val connectivityManager =
            bhagvadGitaApp.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}
package com.example.connectivityobserver




import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.getSystemService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn


interface NetworkMonitor{
    val isOnline:Flow<Boolean>
}

class ConnectivityMonitor (
    private val context :Context
):NetworkMonitor{
    override val isOnline: Flow<Boolean> = callbackFlow {
        val connectivityManager = context.getSystemService<ConnectivityManager>() as ConnectivityManager


        val callBack = object : NetworkCallback() {
            private val networks = mutableSetOf<Network>()

            override fun onAvailable(network: Network) {
                networks += network
                trySend(true)
            }

            override fun onLost(network: Network) {
                networks -= network
                trySend(networks.isNotEmpty())
            }

        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(
           /*NetworkRequest*/ request,
            /*NetworkCallback*/callBack
        )

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callBack)
        }

    }
        .distinctUntilChanged()
        .flowOn(Dispatchers.IO)

}
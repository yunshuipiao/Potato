package com.swensun.swutils.util

import com.swensun.swutils.SwUtils

/**
 * author : zp
 * date : 2021/4/28
 * Potato
 */


import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import com.blankj.utilcode.util.NetworkUtils

/**
 * author : zp
 * date : 2021/3/4
 * zvod
 */

object NetWorkChangeUtils {

    private var networkCallbackListeners = arrayListOf<OnNetworkStatusChangedListener>()

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val networkRequest =
                NetworkRequest.Builder()
                    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .build()
            val cm =
                SwUtils.application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            cm.registerNetworkCallback(
                networkRequest,
                object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network?) {
                        super.onAvailable(network)
                        networkCallbackListeners.forEach {
                            it.onConnected(NetworkUtils.isWifiAvailable())
                        }
                    }

                    override fun onLost(network: Network?) {
                        super.onLost(network)
                        if (NetworkUtils.isConnected() == false) {
                            networkCallbackListeners.forEach {
                                it.onDisconnected()
                            }
                        }
                    }
                })
        } else {
            NetworkUtils.registerNetworkStatusChangedListener(object :
                NetworkUtils.OnNetworkStatusChangedListener {
                override fun onConnected(networkType: NetworkUtils.NetworkType?) {
                    networkCallbackListeners.forEach {
                        it.onConnected(NetworkUtils.isWifiAvailable())
                    }
                }

                override fun onDisconnected() {
                    networkCallbackListeners.forEach {
                        it.onDisconnected()
                    }
                }


            })
        }
    }

    fun register(listener: OnNetworkStatusChangedListener) {
        networkCallbackListeners.add(listener)
    }

    fun unregister(listener: OnNetworkStatusChangedListener) {
        networkCallbackListeners.remove(listener)
    }

    interface OnNetworkStatusChangedListener {
        fun onDisconnected()
        fun onConnected(wifi: Boolean)
    }
}



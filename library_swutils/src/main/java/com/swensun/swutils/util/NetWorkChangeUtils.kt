package com.swensun.swutils.util

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
import android.os.AsyncTask
import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.debounce
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ThreadUtils
import com.swensun.swutils.SwUtils
import java.util.concurrent.CopyOnWriteArrayList

/**
 * author : zp
 * date : 2021/3/4
 * zvod
 */

object NetWorkChangeUtils {

    private var networkCallbackListeners = CopyOnWriteArrayList<OnNetworkStatusChangedListener>()
    private var netWorkChangeLiveData = MutableLiveData<Long>()

    init {

        netWorkChangeLiveData.debounce(800).observeForever {
            if (NetworkUtils.isConnected()) {
                networkCallbackListeners.forEach {
                    AsyncTask.SERIAL_EXECUTOR.execute {
                        val wifi = NetworkUtils.isWifiConnected()
                        ThreadUtils.runOnUiThread {
                            it.onConnected(wifi)
                        }
                    }
                }
            } else {
                networkCallbackListeners.forEach {
                    ThreadUtils.runOnUiThread {
                        it.onDisconnected()
                    }
                }
            }
        }

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
                        netWorkChangeLiveData.postValue(System.currentTimeMillis())
                        log("onAvailable, wifi: ${NetworkUtils.isWifiConnected()}")
                    }

                    override fun onLost(network: Network?) {
                        super.onLost(network)
                        netWorkChangeLiveData.postValue(System.currentTimeMillis())
                        log("onLost")
                    }

                    override fun onCapabilitiesChanged(
                        network: Network?,
                        networkCapabilities: NetworkCapabilities?
                    ) {
                        log("onCapabilitiesChanged, wifi: ${NetworkUtils.isWifiConnected()}")

                    }
                })
        } else {
            NetworkUtils.registerNetworkStatusChangedListener(object :
                NetworkUtils.OnNetworkStatusChangedListener {
                override fun onConnected(networkType: NetworkUtils.NetworkType?) {
                    netWorkChangeLiveData.postValue(System.currentTimeMillis())
                }

                override fun onDisconnected() {
                    netWorkChangeLiveData.postValue(System.currentTimeMillis())
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

    fun log(msg: String) {
        Logger.d("NetWorkChangeUtils, $msg")
    }
}



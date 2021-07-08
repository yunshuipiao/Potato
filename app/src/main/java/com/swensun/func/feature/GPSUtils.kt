package com.swensun.func.feature

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.swensun.swutils.SwUtils

/**
 * author : zp
 * date : 2021/7/5
 * Potato
 */
object GPSUtils {

    private val context by lazy { SwUtils.application.baseContext }

    private val TAG: String = GPSUtils::class.java.simpleName


    private val mLocationListener: LocationListener = object : LocationListener {
        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            Log.d(TAG, "onStatusChanged")
        }

        // Provider被enable时触发此函数，比如GPS被打开
        override fun onProviderEnabled(provider: String) {
            Log.d(TAG, "onProviderEnabled")
        }

        // Provider被disable时触发此函数，比如GPS被关闭
        override fun onProviderDisabled(provider: String) {
            Log.d(TAG, "onProviderDisabled")
        }

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        override fun onLocationChanged(location: Location) {}
    }

    @SuppressLint("MissingPermission")
    fun getLocation(): Location? {
        var location: Location? = null
        try {
            val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (location == null) {
                    location = getLocationByNetwork()
                }
            } else {
                location = getLocationByNetwork()
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return location
    }

    /**
     * 判断是否开启了GPS或网络定位开关
     *
     * @return
     */
    fun isLocationProviderEnabled(): Boolean {
        var result = false
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            result = true
        }
        return result
    }

    /**
     * 获取地理位置，先根据GPS获取，再根据网络获取
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    private fun getLocationByNetwork(): Location? {
        var location: Location? = null
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0f, mLocationListener)
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
        }
        return location
    }
}
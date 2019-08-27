package com.swensun.swutils.shareprefence

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by swensun on 2018/3/22.
 */
object SharePreferencesManager {

    private lateinit var mSharedPreferences: SharedPreferences
    private val keyList = arrayListOf<String>()

    fun init(context: Context) {
        mSharedPreferences  = context.getSharedPreferences("swutils", Context.MODE_PRIVATE)
    }

    fun put(key: String, value: String) {
        mSharedPreferences.edit().putString(key, value).apply()
        keyList.add(key)
    }

    fun put(key: String, value: Int) {
        mSharedPreferences.edit().putInt(key, value).apply()
        keyList.add(key)
    }

    fun put(key: String, value: Long) {
        mSharedPreferences.edit().putLong(key, value).apply()
        keyList.add(key)
    }

    fun put(key: String, value: Float) {
        mSharedPreferences.edit().putFloat(key, value).apply()
        keyList.add(key)
    }

    fun put(key: String, value: Boolean) {
        mSharedPreferences.edit().putBoolean(key, value).apply()
        keyList.add(key)
    }

    operator fun get(key: String, defaultValue: String = ""): String {
        return mSharedPreferences.getString(key, defaultValue) ?: ""
    }

    operator fun get(key: String, defaultValue: Int = -1): Int {
        return mSharedPreferences.getInt(key, defaultValue)
    }

    operator fun get(key: String, defaultValue: Long = -1L): Long {
        return mSharedPreferences.getLong(key, defaultValue)
    }

    operator fun get(key: String, defaultValue: Float = -1f): Float {
        return mSharedPreferences.getFloat(key, defaultValue)
    }

    operator fun get(key: String, defaultValue: Boolean = false): Boolean {
        return mSharedPreferences.getBoolean(key, defaultValue)
    }

    fun deleteSavedData(key: String) {
        mSharedPreferences.edit().remove(key).apply()
    }

    fun deleteAllData() {
        keyList.forEach {
            deleteSavedData(it)
        }
    }
}


public class UidSharePreferencesManager(context: Context, uid: String)  {

    var mSharedPreferences: SharedPreferences? = null
    private val keyList = arrayListOf<String>()

    init {
        mSharedPreferences = context.getSharedPreferences("swutils-uid-$uid", Context.MODE_PRIVATE)
    }
    fun put(key: String, value: String) {
        mSharedPreferences?.let {
            it.edit().putString(key, value).apply()
            keyList.add(key)
        }
    }

    fun put(key: String, value: Int) {
        mSharedPreferences?.let {
            it.edit().putInt(key, value).apply()
            keyList.add(key)
        }
    }

    fun put(key: String, value: Long) {
        mSharedPreferences?.let {
            it.edit().putLong(key, value).apply()
            keyList.add(key)
        }
    }

    fun put(key: String, value: Float) {
        mSharedPreferences?.let {
            it.edit().putFloat(key, value).apply()
            keyList.add(key)
        }
    }

    fun put(key: String, value: Boolean) {
        mSharedPreferences?.let {
            it.edit().putBoolean(key, value).apply()
            keyList.add(key)
        }

    }

    operator fun get(key: String, defaultValue: String = ""): String {
        return mSharedPreferences?.getString(key, defaultValue) ?: defaultValue
    }

    operator fun get(key: String, defaultValue: Int = -1): Int {
        return mSharedPreferences?.getInt(key, defaultValue) ?: defaultValue
    }

    operator fun get(key: String, defaultValue: Long = -1L): Long {
        return mSharedPreferences?.getLong(key, defaultValue) ?: defaultValue
    }

    operator fun get(key: String, defaultValue: Float = -1f): Float {
        return mSharedPreferences?.getFloat(key, defaultValue) ?: defaultValue
    }

    operator fun get(key: String, defaultValue: Boolean = false): Boolean {
        return mSharedPreferences?.getBoolean(key, defaultValue) ?: defaultValue
    }

    fun deleteSavedData(key: String) {
        mSharedPreferences?.edit()?.remove(key)?.apply()

    }

    fun deleteAllData() {
        keyList.forEach {
            deleteSavedData(it)
        }
    }
}

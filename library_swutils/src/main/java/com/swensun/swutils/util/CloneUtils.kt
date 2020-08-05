package com.swensun.swutils.util

import com.google.gson.Gson


inline fun <reified T> T.deepClone(): T {
    val gson = Gson()
    val json = gson.toJson(this)
    return gson.fromJson(json, T::class.java)
}
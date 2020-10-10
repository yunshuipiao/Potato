package com.swensun.swutils.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


inline fun <reified T> T.deepClone(): T {
    val gson = Gson()
    val json = gson.toJson(this)
    return gson.fromJson(json, T::class.java)
}

inline fun <reified T> List<T>.deepClone(): List<T> {
    val gson = Gson()
    val json = gson.toJson(this)
    return gson.fromJson<List<T>>(json, object : TypeToken<List<T>>() {}.type)
}
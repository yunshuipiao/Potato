package com.swensun.swutils.util

object SystemPropUtils {

    fun getProp(key: String): String {
        var result = ""
        try {
            val process = Runtime.getRuntime().exec("getprop log.tag.$key")
            result = process.inputStream.bufferedReader().readLine()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }
}
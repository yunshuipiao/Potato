package com.swensun.potato

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.swensun.swutils.util.Logger

class MainViewModel : ViewModel() {
    fun testCoroutines() {
        (0 until 1000).forEach {
            launchIO {
                formatJson()
                Logger.d("thread:${Thread.currentThread().name}, $it")
            }
        }

    }

    fun formatJson() {
//        Logger.d(" -- start --")
        val startTime = System.currentTimeMillis()
        (0 until 2000).forEach {
            val json = Gson().toJson(Model())
            val model = Gson().fromJson<Model>(json, Model::class.java)
        }
//        Logger.d("-- end ${System.currentTimeMillis() - startTime} --")
    }

    fun test() {
        val str = "#EXT-X-KEY:METHOD    URI=\"Https://baidu.com\", IV=0x000000"
        val regexURI = Regex("URI=\"(.*?)\"")
        val s = regexURI.find(str)
        Logger.d("u: ${s.toString()}")
    }
}

class Model(var id: Int = 0) {
    var count = id
}
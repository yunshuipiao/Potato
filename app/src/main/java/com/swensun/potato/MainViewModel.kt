package com.swensun.potato

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
}

class Model {
    var s1 = "s1"
    var s2 = "s2"
}
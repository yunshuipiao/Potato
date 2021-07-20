package com.swensun.func.livedata

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay

/**
 * author : zp
 * date : 2021/7/20
 * Potato
 */
object LiveDataRepository : CoroutineScope by MainScope(){

    suspend fun testFlow(v: Int): Int {
        delay(200)
        return v
    }
}
package com.swensun.third

import android.util.ArrayMap
import org.junit.Test

class Main {
    @Test
    fun arrayMap_test() {
        arrayListOf(0, -5, 4).forEach {
            println(it.inv())
        }
    }
}

fun String.copy(): String {
    return this + this
}
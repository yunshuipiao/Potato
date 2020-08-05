package com.swensun.potato

import com.swensun.swutils.util.deepClone
import org.junit.Test
import java.io.Serializable

import java.util.ArrayList
import kotlin.reflect.typeOf

class List_Test {

    @Test
    fun remove_test() {
        val timestamp = System.currentTimeMillis()
        val s = (0..10).map { S("$it") }
        (0..3).forEach {
            val ss = s.deepClone()
            println(ss.getOrNull(0))
        }
        println(System.currentTimeMillis() - timestamp)
    }
}

class S(var a: String = "") : Serializable

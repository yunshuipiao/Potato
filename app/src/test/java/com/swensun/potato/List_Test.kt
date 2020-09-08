package com.swensun.potato

import com.swensun.swutils.util.deepClone
import org.junit.Test
import java.io.Serializable

import java.util.ArrayList
import kotlin.reflect.typeOf

class List_Test {

    @Test
    fun add_test() {
        val list = arrayListOf(1, 1, 1, 1, 4, 4, 4, 4, 7, 7, 7, 7)
        val index1 = list.indexOfLast { it == 7 }
        list.addAll(index1 + 1, arrayListOf(2, 2))
        println(list)
    }
}


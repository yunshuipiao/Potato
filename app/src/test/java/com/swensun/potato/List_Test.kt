package com.swensun.potato

import org.junit.Test
import java.util.concurrent.CopyOnWriteArrayList

class List_Test {

    @Test
    fun add_test() {
        test()
    }

    @Synchronized
    private fun test() {
        val l = CopyOnWriteArrayList<Int>()
        l.add(1)
        l.add(2)
        l.add(3)
        l.add(4)
        l.forEach {
            if (it == 2) {
                remove(l, it)
            }
        }
    }

    fun remove(l: CopyOnWriteArrayList<Int>, it: Int) {
        l.remove(it)
    }

    @Synchronized
    fun remove(l: ArrayList<Int>, it: Int) {
        l.remove(it)
    }
}


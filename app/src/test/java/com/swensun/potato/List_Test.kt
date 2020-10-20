package com.swensun.potato

import org.junit.Test

class List_Test {

    @Test
    fun add_test() {
        var l = arrayListOf<RInt>()
        l.addAll((0 until 10).map {
            RInt(it)
        })
        l.addAll((5 until 15).map { RInt(it) })
        println(l.distinctBy { it.id }.map { "${it.id}--${it.count}" })
    }
}

class RInt(var id: Int) {
    var count = id
}


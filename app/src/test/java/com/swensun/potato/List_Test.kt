package com.swensun.potato

import org.junit.Test

import java.util.ArrayList

class List_Test {

    @Test
    fun remove_test() {
        val al = arrayListOf<S>().apply {
            add(S("a"))
            add(S("b"))
            add(S("c"))
        }
        val bl = arrayListOf<S>().apply {
            add(S("a"))
            add(S("b"))
            add(S("c"))
        }

        val p = al.zip(bl)
        val result  = p.all {
            it.first.a == it.second.a
        }
        println(result)
    }
}

class S(var a: String = "") {

}

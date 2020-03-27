package com.swensun.potato

import org.junit.Test

class L {
    @Test
    fun t() {
        val l = arrayListOf(A(), B())
        l.forEach {
            when {
                (it is A) -> {

                }
            }
        }
    }
}

interface Visible {

}

class A : Visible {
    fun a() {
        println()
    }
}

class B : Visible {
    fun b() {
        println()
    }
}
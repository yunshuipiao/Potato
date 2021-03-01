package com.swensun.potato

import org.junit.Test

class Get {

    @Test
    fun test() {
        val ll = II()
        println(ll.p)
        ll.p = 20
        println(ll.p)
    }

}

interface I {
    var p: Int
}

class II : I {
    override var p: Int = 0
        get() {
            field = 9
            return 4 + 5
        }
        set(value) {
            field = value
        }
}
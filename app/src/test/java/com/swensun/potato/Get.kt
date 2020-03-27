package com.swensun.potato

class Get {
    var today = System.currentTimeMillis()
    set(value) {
        field = value + 1
    }

    var today1 = 0L
    get() = System.currentTimeMillis()
    set(value) {
        field = value + 1
    }
}
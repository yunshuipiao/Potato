package com.swensun.potato

import kotlinx.coroutines.*
import org.junit.Test
import kotlin.system.measureTimeMillis

class Coroutines_Test {

    @Test
    fun async_test() {
        println(measureTimeMillis {
            println("-- 1 --")
            runBlocking(Dispatchers.IO) {
                async { asyncInt() }
                async { asyncString() }.await()

            }
            println("-- 2 --")
        })
    }


    suspend fun asyncInt(): Int {
        println("thread: ${Thread.currentThread().name}")
        delay(1000)
        return 2
    }


    suspend fun asyncString(): String {
        println("thread: ${Thread.currentThread().name}")
        delay(1500)
        return "result"
    }
}
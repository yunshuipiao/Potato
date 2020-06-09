package com.swensun.potato

import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class CountDownLaunchTest {

    @Test
    fun test() {
        println("--- start ---")
        val startTime = System.currentTimeMillis()
        val result = getResult()
        println("result: $result -- " + (System.currentTimeMillis() - startTime))
    }

    fun getResult(): String {
        val countDownLatch = CountDownLatch(5)
        var result = "0"
        Thread {
            Thread.sleep(1000)
            result = "1"
            countDownLatch.countDown()

        }.start()
        Thread {
            Thread.sleep(2000)
            result = "2"
            countDownLatch.countDown()

        }.start()
        Thread {
            Thread.sleep(3000)
            result = "3"
            countDownLatch.countDown()

        }.start()
        countDownLatch.await(1500, TimeUnit.MILLISECONDS)
        return result
    }
}
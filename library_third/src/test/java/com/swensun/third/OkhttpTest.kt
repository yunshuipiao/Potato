package com.swensun.third

import android.util.Log
import com.orhanobut.logger.Logger
import org.junit.Test

import org.junit.Assert.assertEquals
import okhttp3.OkHttpClient
import android.R.string
import okhttp3.Request
import org.junit.experimental.results.ResultMatchers.isSuccessful
import java.io.IOException


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class OkhttpTest {

    val  client = OkHttpClient()

    @Test
    fun addition_isCorrect() {
        println("message")
        assertEquals(4, (2 + 2).toLong())
    }

    @Test
    fun syncGetTest() {
        val request = Request.Builder()
            .url("https://publicobject.com/helloworld.txt")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val responseHeaders = response.headers()
            for (i in 0 until responseHeaders.size()) {
                println(responseHeaders.name(i) + ": " + responseHeaders.value(i))
            }

            System.out.println(response.body()?.string())
        }
    }
}

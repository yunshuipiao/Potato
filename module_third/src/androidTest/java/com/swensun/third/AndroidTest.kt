package com.swensun.third

import android.content.Context
import android.util.ArrayMap
import android.util.Log
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AndroidTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        Log.d("123", "message")
        assertEquals("com.swensun.third.test", appContext.packageName)
    }

    @Test
    fun arrayMap_test() {
        val arrayMap = ArrayMap<String, Int>()
        arrayMap.put("1", 1)
        arrayMap.get("1")
        arrayListOf(0, -1, 4).forEach {
            println(it.inv())
        }
    }
}
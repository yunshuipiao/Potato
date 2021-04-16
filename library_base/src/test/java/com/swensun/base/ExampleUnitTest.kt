package com.swensun.base

import com.google.gson.Gson
import com.swensun.func.fromJson
import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, 2 + 2.toLong())
    }

    @Test
    fun fromJsonTest() {
        val gson = Gson()
        val u = arrayListOf(User(), User())
        val json = gson.toJson(u)
        println(json)
        val uu = json.fromJson<Collection<User>>()
        println(uu)
    }
}

class User {
    val name = "sw"
    val sch = mapOf("1" to "one")

    override fun toString(): String {
        return "name: ${name}, sch:${sch}"
    }
}
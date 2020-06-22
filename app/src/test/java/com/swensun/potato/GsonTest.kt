package com.swensun.potato

import com.google.gson.GsonBuilder
import com.swensun.http.StringTypeAdapter
import org.json.JSONObject
import org.junit.Test


val userJson = "{\"name\":null,\"like\":null,\"age\":0}"

class GsonTest {

    val gson = GsonBuilder()
        .registerTypeAdapter(String::class.java, StringTypeAdapter())
        .create()

    @Test
    fun gsonTest() {

        var json = gson.toJson(Blank())
        println(json)
        val jsonObject = JSONObject(json)
        println(jsonObject.optJSONObject("inner"))
    }
}

class Blank {
    var name = "blank"
    val inner = InnerBlank()

}

class InnerBlank {
    val name = "innerBlank"
}


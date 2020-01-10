package com.swensun.potato

import com.blankj.utilcode.util.GsonUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.swensun.http.StringTypeAdapter
import org.json.JSONObject
import org.junit.Assert
import org.junit.Test


val userJson = "{\"name\":null,\"like\":null,\"age\":0}"

class GsonTest {

    val gson = GsonBuilder()
        .registerTypeAdapter(String::class.java, StringTypeAdapter())
        .create()

    @Test
    fun gsonTest() {
        println("-- gson test -- ")

        val userInfo = UserInfo().apply {
            name = "sw"
            like = "swsw"
            age = 11
        }
        val json = gson.toJson(userInfo)
        println(json)

        var user = gson.fromJson(userJson, UserInfo::class.java)
        println(user)
    }
}



class UserInfo {
    var name = ""
    var like: String = ""
    var age = 0

    override fun toString(): String {
        return "name: $name, like, $like, age: $age"
    }
}

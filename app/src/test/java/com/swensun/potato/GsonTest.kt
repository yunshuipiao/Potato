package com.swensun.potato

import com.blankj.utilcode.util.GsonUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import org.json.JSONObject
import org.junit.Assert
import org.junit.Test

val str1 = "{\"school\":\"\",\"id\":0}"
val str2 = "{\"school\":\"\",\"className\":null,\"userInfo\":null,\"id\":null}"
val str3 =
    "{\"school\":\"\",\"className\":\"\",\"userInfo\":{\"name\":\"\",\"like\":\"\",\"age\":0},\"users\":null,\"id\":10}"
val str4 =
    "{\"school\":\"\",\"className\":\"\",\"userInfo\":{\"name\":\"\",\"like\":\"\",\"age\":0},\"users\":[],\"map\":{},\"id\":10}"

class GsonTest {
    @Test
    fun gsonTest() {
        println("-- gson test -- ")
        val gson = GsonBuilder().serializeNulls().create()
        val student = gson.fromJson(str2, Student::class.java)
        println("student: ${student.userInfo}")
//        println("str: " + gson.toJson(Student()))
    }
}

data class Student(var id: Int = 10) {
    var school = ""
    var className: String = ""
    get() = if (field == null) "" else field

    var userInfo: UserInfo? = UserInfo()
        get() = if (field == null) { UserInfo() } else { field }
    var users = arrayListOf<UserInfo>()
    var map = hashMapOf<String, Int>()
}


class UserInfo {
    var name = ""
    var like: String = ""
    var age = 0
}

class A {
    var a = ""
    @Test
    fun test() {
        val json = "{\"a\":null}"

        val obj = Gson().fromJson(json.toString(),A::class.java)

        Assert.assertNotNull(obj.a)
    }
}
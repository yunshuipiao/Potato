package com.swensun.potato

import com.blankj.utilcode.util.GsonUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.junit.Test

val str1 = "{\"school\":\"\",\"id\":0}"
val str2 = "{\"school\":\"\",\"className\":null,\"userInfo\":{},\"id\":null}"
val str3 = "{\"school\":\"\",\"className\":\"\",\"userInfo\":{\"name\":\"\",\"like\":\"\",\"age\":0},\"users\":null,\"id\":10}"


class GsonTest {
    @Test
    fun gsonTest() {
        println("-- gson test -- ")
        val gson = GsonBuilder().serializeNulls().create()
//        val student = gson.fromJson(str3, Student::class.java)
//        println("student: ${student.userInfo}")
        println("str: " + gson.toJson(Student()))
    }
}

data class Student(var id: Int = 10) {
    var school = ""
    var className: String = ""
    var userInfo = UserInfo()
    var users = arrayListOf<UserInfo>()
    var map = hashMapOf("1" to 1)

//    override fun toString(): String {
//        return "school: $school, className: $className, userinfo: ${userInfo.name} -- ${userInfo.like}"
//    }

}

class UserInfo {
    var name = ""
    var like: String = ""
    var age = 0
}
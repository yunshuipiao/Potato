package com.swensun.plugin.time

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class TimePlugin : Plugin<Project> {
    override fun apply(target: Project) {


        val android = target.extensions.getByType(AppExtension::class.java)
        android.registerTransform(TimeTransform(target))
        println("--------------")
        println("Hello ---  TimePlugin")
        println("--------------")
    }
}
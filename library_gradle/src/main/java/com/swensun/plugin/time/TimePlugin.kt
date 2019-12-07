package com.swensun.plugin.time

import org.gradle.api.Plugin
import org.gradle.api.Project

class TimePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("--------------")
        println("Hello ---  TimePlugin")
        println("--------------")
    }
}
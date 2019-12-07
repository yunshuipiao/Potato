package com.swensun.plugin.time

import org.gradle.api.Plugin
import org.gradle.api.Project


public class TimePlugin implements Plugin<Project> {
    void apply(Project project) {
        System.out.println("========================");
        System.out.println("Hello TimePlugin!");
        System.out.println("========================");
       }
}
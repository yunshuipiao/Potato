package com.swensun.base

import android.content.Context

/**
 * @Date 2019-10-10
 * @author sunwen
 * @Project AndroidDemo
 */
object BaseUtils {

    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context
    }
}
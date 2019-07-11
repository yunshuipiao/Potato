package com.swensun.swutils.util

import com.swensun.swutils.ui.getColor
import java.util.*

val colors = arrayListOf("#F44336",
        "#E91E63", "#9C27B0", "#3F51B5", "#2196F3", "#009688",
        "#4CAF50", "#FF9800", "#FF5722", "#795548", "#607D8B")

fun ArrayList<String>.random(): Int {
    val result =  Random().nextInt(size)
    return getColor(this[result])
}
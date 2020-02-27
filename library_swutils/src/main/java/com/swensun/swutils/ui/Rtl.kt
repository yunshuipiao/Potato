package com.swensun.swutils.ui

import android.text.TextUtils
import com.swensun.swutils.util.Logger

fun rtl(str: String): String {
    return "\u202b" + str + "\u202b"
}

fun isRtl(string: String): Boolean {
    if (TextUtils.isEmpty(string)) {
        return false
    }
    var i = 0
    val n = string.length
    while (i < n) {
        Logger.d("$i, ${Character.getDirectionality(string[i])}")
        when (Character.getDirectionality(string[i])) {
            Character.DIRECTIONALITY_RIGHT_TO_LEFT,
            Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC,
            Character.DIRECTIONALITY_RIGHT_TO_LEFT_EMBEDDING,
            Character.DIRECTIONALITY_RIGHT_TO_LEFT_OVERRIDE -> return true
            
            Character.DIRECTIONALITY_LEFT_TO_RIGHT,
            Character.DIRECTIONALITY_LEFT_TO_RIGHT_EMBEDDING,
            Character.DIRECTIONALITY_LEFT_TO_RIGHT_OVERRIDE -> return false
        }
        ++i
    }
    return false
}
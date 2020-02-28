package com.swensun.swutils.ui

import android.text.TextUtils
import com.swensun.swutils.util.Logger

fun rtl(str: String): String {
    return "\u202b" + str + "\u202b"
}

fun ltr(str: String): String {
    return "\u202e" + str + "\u202e"
}

fun isRtl(string: String): Boolean {
    if (TextUtils.isEmpty(string)) {
        return false
    }
    string.forEachIndexed { index, char ->
//        Logger.d("$index, ${Character.getDirectionality(char)}")
        when (Character.getDirectionality(char)) {
            Character.DIRECTIONALITY_RIGHT_TO_LEFT,
            Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC,
            Character.DIRECTIONALITY_RIGHT_TO_LEFT_EMBEDDING,
            Character.DIRECTIONALITY_RIGHT_TO_LEFT_OVERRIDE -> Logger.d("$char  true")

            Character.DIRECTIONALITY_LEFT_TO_RIGHT,
            Character.DIRECTIONALITY_LEFT_TO_RIGHT_EMBEDDING,
            Character.DIRECTIONALITY_LEFT_TO_RIGHT_OVERRIDE -> Logger.d("$char  false")
        }
    }
    return false
}
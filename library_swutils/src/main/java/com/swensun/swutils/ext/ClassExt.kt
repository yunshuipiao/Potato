package com.swensun.swutils.ext

/**
 * author : zp
 * date : 2021/5/13
 * Potato
 */


inline fun <reified T> newInstance(vararg params: Any): T =
    T::class.java.getDeclaredConstructor(*params.map { it::class.java }.toTypedArray())
        .apply { isAccessible = true }.newInstance(*params)
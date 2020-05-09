package com.swensun.func.trans

import android.graphics.Typeface
import com.swensun.swutils.SwUtils
import java.util.concurrent.ConcurrentHashMap

private val map = ConcurrentHashMap<String, Typeface>()

val alkaTipTorTom: Typeface?
    get() = createFromAsset("fonts/ALKATIP_Tor_Tom.ttf")

val ukijqara by lazy { createFromAsset("fonts/ukijqara.ttf") }


fun createFromAsset(path: String): Typeface? {
    if (map.containsKey(path)) {
        return map[path]
    }

    // 6.0及以下版本，找不到字体时有可能抛错
    try {
        val typeface = Typeface.createFromAsset(SwUtils.application.assets, path)

        map[path] = typeface

        return typeface
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return null
}

interface TypefaceChangeCallBack {

    fun getCurrentTypeFace(): Typeface?
}

object FontHelper {

    private var typeFaceChangeCallBack: TypefaceChangeCallBack = object: TypefaceChangeCallBack{
        override fun getCurrentTypeFace(): Typeface? {
            return ukijqara
        }
    }
    /**
     * 字体设置的回调
     */
    public fun registerTypeFaceChangeCallback(typeFaceChangeCallBack: TypefaceChangeCallBack) {
        this.typeFaceChangeCallBack = typeFaceChangeCallBack
    }

    public fun getCurrentTypeFace(): Typeface? {
        return typeFaceChangeCallBack.getCurrentTypeFace()
    }
}
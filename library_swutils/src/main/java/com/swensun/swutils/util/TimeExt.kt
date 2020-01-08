package com.swensun.swutils.util

import android.os.Handler

private var lastTime = System.currentTimeMillis()
private var handler = Handler()

fun debounce(delayMillis: Long, block: () -> Unit) {
    handler.postDelayed(block, delayMillis)
    lastTime = System.currentTimeMillis()
    if (System.currentTimeMillis() - lastTime < delayMillis) {
        lastTime = System.currentTimeMillis()
        handler.removeCallbacks(block)
    }
}
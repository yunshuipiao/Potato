package com.dueeeke.videoplayer.util

import android.view.View

/**
 * author : zp
 * date : 2020/11/14
 * Potato
 */

var View.visible: Boolean
    get() {
        return visibility == View.VISIBLE
    }
    set(value) {
        visibility = if (value) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
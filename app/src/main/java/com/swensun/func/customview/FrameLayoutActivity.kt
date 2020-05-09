package com.swensun.func.customview

import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.ViewTarget
import com.bumptech.glide.request.transition.Transition
import com.swensun.potato.R
import com.swensun.swutils.ui.dp2px
import com.swensun.swutils.ui.px2dp
import kotlinx.android.synthetic.main.activity_frame_layout.*
import org.jetbrains.anko.toast
import java.text.DecimalFormat
import kotlin.math.log10
import kotlin.math.pow

class FrameLayoutActivity : AppCompatActivity() {

    var iconUrl = "http://assets.zvod.badambiz.com/thumb_330x440_1584539045991.jpg"
    var rule = "?imageView2/2/w/250"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame_layout)

        tv_bg.setOnClickListener {
            toast(it)
        }
        btn_load.setOnClickListener {
            Glide.with(this)
                .asBitmap()
                .load(R.drawable.cat)
//                .sizeMultiplier(0.5f)
//                .override(tv_bg.width, tv_bg.height)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(
                        bitmap: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        toast("bitmap :${bitmap.width}, ${bitmap.height}, ${readableFileSize(bitmap.allocationByteCount.toLong())}")
                        tv_bg.background = BitmapDrawable(resources, bitmap)
                    }
                })
        }
        iv_bg.setOnClickListener {
            toast(it)
        }
    }
    private fun toast(view: View) {
        toast("width: ${view.width}, height, ${view.height}")
    }

    private fun readableFileSize(size: Long): String {
        if (size <= 0) {
            return "0"
        }
        val units = arrayOf("B", "kB", "MB", "GB", "TB")
        val digitGroups = (log10(size.toDouble()) / log10(1024.0)).toInt()
        return DecimalFormat("#,##0.#").format(size / 1024.0.pow(digitGroups.toDouble())) + " " + units[digitGroups]
    }
}

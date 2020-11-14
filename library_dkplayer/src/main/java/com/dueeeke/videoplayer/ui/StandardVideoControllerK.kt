package com.dueeeke.videoplayer.ui

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import com.dueeeke.videoplayer.R
import com.dueeeke.videoplayer.controller.GestureVideoController
import kotlinx.android.synthetic.main.dkplayer_layout_standard_controller.view.*


/**
 * 直播/点播控制器
 * 注意：此控制器仅做一个参考，如果想定制ui，你可以直接继承GestureVideoController或者BaseVideoController实现
 * 你自己的控制器
 * Created by dueeeke on 2017/4/7.
 */
class StandardVideoControllerK @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : GestureVideoController(context, attrs, defStyleAttr) {

    override fun getLayoutId(): Int {
        return R.layout.dkplayer_layout_standard_controller
    }

    override fun initView() {
        super.initView()

        lock.setOnClickListener {

        }
    }

}
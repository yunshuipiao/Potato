package com.swensun.func.anim

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.swensun.base.BaseActivity
import com.swensun.potato.R
import com.swensun.swutils.ui.getWinHeight
import kotlinx.android.synthetic.main.activity_anim.*

class AnimActivity : BaseActivity() {
    override fun getContentSubView(): Int {
        return R.layout.activity_anim
    }

    override fun initView(savedInstanceState: Bundle?) {
        btn_show.setOnClickListener {
            tv_right.animate()
                .translationX(((0 - tv_right.width).toFloat()))
                .setDuration(200)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                    }
                })
        }
        btn_hide.setOnClickListener {
            tv_right.animate()
                .translationX(0f)
                .setDuration(200)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                    }
                })
        }
    }
}
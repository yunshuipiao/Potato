package com.swensun.func.anim

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import com.swensun.base.Base2Activity
import com.swensun.potato.databinding.ActivityAnimBinding

class AnimActivity : Base2Activity<ActivityAnimBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        vb.btnShow.setOnClickListener {
            vb.tvRight.animate()
                .translationX(((0 - vb.tvRight.width).toFloat()))
                .setDuration(200)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                    }
                })
        }
        vb.btnHide.setOnClickListener {
            vb.tvRight.animate()
                .translationX(0f)
                .setDuration(200)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                    }
                })
        }
    }
}
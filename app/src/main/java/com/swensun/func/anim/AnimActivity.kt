package com.swensun.func.anim

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import com.swensun.base.BaseActivity
import com.swensun.potato.databinding.ActivityAnimBinding

class AnimActivity : BaseActivity<ActivityAnimBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        binding.btnShow.setOnClickListener {
            binding.tvRight.animate()
                .translationX(((0 - binding.tvRight.width).toFloat()))
                .setDuration(200)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                    }
                })
        }
        binding.btnHide.setOnClickListener {
            binding.tvRight.animate()
                .translationX(0f)
                .setDuration(200)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                    }
                })
        }
    }
}
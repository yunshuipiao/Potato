package com.swensun.func.trans

import android.os.Bundle
import com.blankj.utilcode.util.LanguageUtils
import com.swensun.base.BaseActivity
import com.swensun.potato.databinding.ActivityTransFontBinding
import java.util.*

class TransFontActivity : BaseActivity<ActivityTransFontBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        binding.ivRed.setOnClickListener {
            LanguageUtils.applyLanguage(Locale("ug", "CN"), true)
        }
        binding.ivBlue.setOnClickListener {
            LanguageUtils.applyLanguage(Locale("zh", "CN"), true)
        }
    }

}

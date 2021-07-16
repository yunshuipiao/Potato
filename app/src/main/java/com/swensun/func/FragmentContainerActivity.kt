package com.swensun.func

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.swensun.base.BaseActivity
import com.swensun.potato.databinding.FragmentContainerActivityBinding
import com.swensun.swutils.util.startActivity

/**
 * author : zp
 * date : 2021/7/16
 * Potato
 */


inline fun <reified T> Context.startFragmentContainerActivity(bundle: Bundle? = null) {
    val fragmentName = T::class.java.name
    startActivity<FragmentContainerActivity>("fragment" to fragmentName, "bundle" to bundle)
}

class FragmentContainerActivity: BaseActivity<FragmentContainerActivityBinding>() {
    
    override fun initView(savedInstanceState: Bundle?) {
        val fragmentName = intent?.getStringExtra("fragment") ?: ""
        val bundle = intent?.getBundleExtra("bundle")
        if (savedInstanceState == null && fragmentName.isNotBlank()) {
            val fragment = Class.forName(fragmentName).newInstance() as Fragment
            fragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .disallowAddToBackStack()
                .replace(binding.container.id, fragment)
                .commit()
        } else {
            finish()
        }
    }
}
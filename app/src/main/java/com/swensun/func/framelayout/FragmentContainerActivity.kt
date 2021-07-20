package com.swensun.func.framelayout

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.swensun.base.fitStatusBarHeight
import com.swensun.base.setTransparentStatusBar
import com.swensun.swutils.ui.context
import com.swensun.swutils.util.startActivity

/**
 * author : zp
 * date : 2021/7/16
 * Potato
 */


inline fun <reified T: Fragment> Context.startFragmentContainerActivity(bundle: Bundle? = null) {
    val fragmentName = T::class.java.name
    startActivity<FragmentContainerActivity>("fragment" to fragmentName, "bundle" to bundle)
}

class FragmentContainerActivity: AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val frameLayout = FrameLayout(context)
        setContentView(frameLayout)
        frameLayout.id = View.generateViewId()
        setTransparentStatusBar()
        fitStatusBarHeight(frameLayout)
        val fragmentName = intent?.getStringExtra("fragment") ?: ""
        val bundle = intent?.getBundleExtra("bundle")
        if (savedInstanceState == null) {
            val fragment = Class.forName(fragmentName).newInstance() as Fragment
            fragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .disallowAddToBackStack()
                .replace(frameLayout.id, fragment)
                .commit()
        } else {
            finish()
        }
    }
}
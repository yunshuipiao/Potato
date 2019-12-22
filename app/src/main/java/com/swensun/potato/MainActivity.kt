package com.swensun.potato

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.coroutineScope
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ScreenUtils
import com.swensun.base.BaseActivity
import com.swensun.base.BaseUtils
import com.swensun.potato.demo.RecyclerViewActivity
import com.swensun.swutils.ui.StatusBarUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class MainActivity : BaseActivity() {

    lateinit var mainViewModel: MainViewModel
    lateinit var userViewModel: UserViewModel

    companion object {
        var i = 100
    }

    override fun getContentSubView(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        userViewModel = ViewModelProviders.of(this, SharedViewModelFactory).get(UserViewModel::class.java)
//        userViewModel.name.observe(this, Observer<String> {
//            log.text = it
//            toast("main $it")
//        })
        initView(savedInstanceState)
    }

    private fun initView(savedInstanceState: Bundle?) {
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, LayoutFragment.newInstance())
//                .commit()
//        }
        lifecycle.coroutineScope
        log.text = i.toString()
        log.setOnClickListener {
//            val key = "test"
//            Logger.d("getProp: $key = ${SystemPropUtils.getProp(key)}")
//            Logger.i("getProp: $key = ${SystemPropUtils.getProp(key)}")
            test()
            
        }
    }

    fun test() {
        startActivity<RecyclerViewActivity>()
    }
}


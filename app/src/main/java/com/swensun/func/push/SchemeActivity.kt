package com.swensun.func.push

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
import com.swensun.base.BaseActivity
import com.swensun.potato.databinding.SchemeActivityBinding
import com.swensun.swutils.ui.context
import com.swensun.swutils.util.Logger
import org.jetbrains.anko.toast

class SchemeActivity : BaseActivity<SchemeActivityBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Logger.d("PushActivity__ onNewIntent")
        handleIntent(intent)
    }


    private fun handleIntent(intent: Intent?) {
        val uri = intent?.data
        if (uri != null) {
            val action = uri.getQueryParameter("action") ?: ""
            val pkg = uri.getQueryParameter("pkg") ?: ""
            val url = uri.getQueryParameter("url") ?: ""
            val act = uri.getQueryParameter("act") ?: ""
            when (action) {
                "1" -> {
                    //打开act
                    Logger.d("act: $act")
                    try {
                        var cls = Class.forName(act)
                        val newIntent = Intent()
                        newIntent.setClass(context, cls)
                        if (context !is Activity) {
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        startActivity(newIntent)
                        finish()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                "2" -> {
                    if (pkg.isNotBlank() && AppUtils.isAppInstalled(pkg)) {
                        AppUtils.launchApp(pkg)
                    }
                }
                "3" -> {

                }
                else -> {
                    toast("action: $action")
                }
            }
        }
        vb.tvTitle.setOnClickListener {
            val acts = ActivityUtils.getActivityList()
            Logger.d("act: $acts")
        }
    }

}
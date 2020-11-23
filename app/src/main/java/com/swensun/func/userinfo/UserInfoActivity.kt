package com.swensun.func.userinfo

import android.os.Bundle
import androidx.lifecycle.Observer
import com.swensun.base.BaseActivity
import com.swensun.potato.R
import com.swensun.potato.UserInfo
import com.swensun.potato.UserInfoRepository
import com.swensun.swutils.util.Logger
import kotlinx.android.synthetic.main.activity_user_info.*

class UserInfoActivity : BaseActivity() {
    override fun getContentSubView(): Int {
        return R.layout.activity_user_info
    }

    override fun initView(savedInstanceState: Bundle?) {

        UserInfoRepository.userInfoLiveData.observe(this, Observer {
            Logger.d("userinfo activity change: $it")
        })
        UserInfoRepository.userVipStateChanged.observe(this, Observer {
            Logger.d("userinfo activity vip change: $it")
        })

        UserInfoRepository.userLoginStateChanged.observe(this, Observer {
            Logger.d("userinfo activity login change: $it")
        })

        btn_update.setOnClickListener {
            val name = et_name.text.toString()
            val login = rb_login.isChecked
            val vip = rb_vip.isChecked
            val coins = seek_bar.progress
            val userInfo = UserInfo().apply {
                this.name = name
                this.login = login
                this.vip = vip
                this.coins = coins
            }
            UserInfoRepository.updateUserInfo(userInfo)
        }
    }
}
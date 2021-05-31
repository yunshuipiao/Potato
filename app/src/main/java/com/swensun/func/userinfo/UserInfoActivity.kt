package com.swensun.func.userinfo

import android.os.Bundle
import androidx.lifecycle.Observer
import com.swensun.base.Base2Activity
import com.swensun.potato.UserInfo
import com.swensun.potato.UserInfoRepository
import com.swensun.potato.databinding.ActivityUserInfoBinding
import com.swensun.swutils.util.Logger

class UserInfoActivity : Base2Activity<ActivityUserInfoBinding>() {

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

        vb.btnUpdate.setOnClickListener {
            val name = vb.etName.text.toString()
            val login = vb.rbLogin.isChecked
            val vip = vb.rbVip.isChecked
            val coins = vb.seekBar.progress
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


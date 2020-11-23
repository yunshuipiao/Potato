package com.swensun.potato

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

/**
 * author : zp
 * date : 2020/11/23
 * Potato
 */


object UserInfoRepository {

    var userVipStateChanged = MediatorLiveData<UserInfo>()
    var userInfoLiveData = MutableLiveData<UserInfo>()
    var userLoginStateChanged = MediatorLiveData<UserInfo>()

    init {
        userLoginStateChanged.addSource(userInfoLiveData) {
            val oldUserInfo = userLoginStateChanged.value
            if (oldUserInfo?.login != it?.login) {
                userLoginStateChanged.postValue(it)
            }
        }

        userVipStateChanged.addSource(userInfoLiveData) {
            val oldUserInfo = userVipStateChanged.value
            if (oldUserInfo?.vip != it?.vip) {
                userVipStateChanged.postValue(it)
            }
        }
    }

    var userInfo = UserInfo()

    fun updateUserInfo(info: UserInfo) {
        userInfo.id = info.id
        userInfo.coins = info.coins
        userInfo.vip = info.vip
        userInfo.login = info.login
        userInfo.name = info.name
        userInfoLiveData.postValue(userInfo)
    }
}

class UserInfo {
    var id = 0
    var name = ""
    var vip = false
    var login = false
    var coins = 0

    override fun toString(): String {
        return "id: $id \n " +
                "name: $name \n" +
                "coins: $coins \n" +
                "vip $vip \n" +
                "login: $login"
    }
}

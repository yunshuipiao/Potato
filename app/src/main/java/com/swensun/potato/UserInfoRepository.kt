package com.swensun.potato

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * author : zp
 * date : 2020/11/23
 * Potato
 */

object UserInfoRepository : CoroutineScope {

    var userVipStateChanged = MediatorLiveData<UserInfo>()
    val userInfoLiveData = MutableLiveData<UserInfo>()
    val userInfo: UserInfo
        get() = userInfoLiveData.value ?: UserInfo()
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

    fun updateUserInfo(info: UserInfo) {
        val userInfo = UserInfo()
        userInfo.id = info.id
        userInfo.coins = info.coins
        userInfo.vip = info.vip
        userInfo.login = info.login
        userInfo.name = info.name
        userInfoLiveData.postValue(userInfo)

    }

    fun test() {
    }


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()
}

data class UserInfo(
    var id: Int = 0,
    var name: String = "",
    var vip: Boolean = false,
    var login: Boolean = false,
    var coins: Int = 0)

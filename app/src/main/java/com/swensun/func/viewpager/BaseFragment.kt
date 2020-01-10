package com.swensun.func.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
    private var isViewCreated = false //view是否创建
    private var isVisibleToUser = false // 是否对用户可见
    private var isDataLoaded = false // 数据是否请求

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        _loadData()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.isViewCreated = true
        _loadData()
    }

    private fun _loadData() {
        if (isViewCreated && isVisibleToUser && !isDataLoaded && isParentFragmentVisible()) {
            loadData()
            isDataLoaded = true
            dispatchParentVisibleState()
        }
    }

    protected open fun loadData(){
        
    }

    /**
     * 在 viewpager 场景中判断父 fragment 是否可见
     */
    private fun isParentFragmentVisible(): Boolean {
        val fragment = parentFragment ?: return true
        if (fragment is BaseFragment && fragment.isVisibleToUser) {
            return true
        }
        return false
    }

    private fun dispatchParentVisibleState() {
        if (!isAdded) {
            return 
        }
        val fragmentManager = childFragmentManager
        val fragments = fragmentManager.fragments
        if (fragments.isEmpty()) {
            return
        }
        fragments.forEach { child ->
            if (child is BaseFragment && child.isVisibleToUser) {
                child._loadData()
            }
        }
    }
}
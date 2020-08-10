package com.swensun.func.utilcode

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.AppUtils
import com.swensun.potato.R
import com.swensun.swutils.util.Logger
import kotlinx.android.synthetic.main.util_code_fragment.*

class UtilCodeFragment : Fragment() {

    companion object {
        fun newInstance() = UtilCodeFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.util_code_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initView()
    }

    private fun initView() {
        btn_test.setOnClickListener {
            val infos = AppUtils.getAppsInfo()
            infos.map { it.versionName }.forEach {
                Logger.d("info:${it}")
            }
        }
    }
}
package com.swensun.func.lifecycle

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.swensun.potato.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.lifecycle_fragment.*
import org.jetbrains.anko.support.v4.toast

class LifecycleFragment : Fragment() {

    companion object {
        fun newInstance() = LifecycleFragment()
    }

    private lateinit var viewModel: LifecycleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.lifecycle_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LifecycleViewModel::class.java)
        initView()
    }

    private fun initView() {

        viewModel.strLiveData.observe(this, Observer {
            toast(it)
        })
        btn_post_self.setOnClickListener {
             viewModel.postLifecycler()
        }
    }
}

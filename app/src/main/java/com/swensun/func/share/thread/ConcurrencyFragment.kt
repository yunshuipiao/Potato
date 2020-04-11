package com.swensun.func.share.thread

import android.annotation.SuppressLint
import android.app.Service
import android.os.AsyncTask
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.ProcessUtils
import com.swensun.potato.R
import com.swensun.swutils.ui.getColor
import com.swensun.swutils.util.Logger
import kotlinx.android.synthetic.main.concurrency_fragment.*
import org.jetbrains.anko.doAsync
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor

class ConcurrencyFragment : Fragment() {

    companion object {
        fun newInstance() = ConcurrencyFragment()
    }

    private val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            if (msg?.what == 1) {
                change_ui.setBackgroundColor(getColor("#00ff00"))
            }
        }
    }

    private lateinit var viewModel: ConcurrencyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.concurrency_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ConcurrencyViewModel::class.java)

        initView()
    }

    private fun initView() {
        change_ui.setOnClickListener {
            //1.耗时操作
//            Thread.sleep(6000)

//            2.更新UI
            AsyncTask.THREAD_POOL_EXECUTOR.execute {
                Thread.sleep(1000)
//                change_ui.post { change_ui.setBackgroundColor(getColor("#ff0000")) }

//             3. handler
                handler.sendEmptyMessage(1)
            }
        }
    }
}

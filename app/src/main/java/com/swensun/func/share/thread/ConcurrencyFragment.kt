package com.swensun.func.share.thread

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.swensun.potato.R
import com.swensun.swutils.ui.getColor
import kotlinx.android.synthetic.main.concurrency_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
//            AsyncTask.THREAD_POOL_EXECUTOR.execute {
//                Thread.sleep(1000)
////                change_ui.post { change_ui.setBackgroundColor(getColor("#ff0000")) }
//
////             3. handler
//                handler.sendEmptyMessage(1)
//            }

            lifecycleScope.launch {
                val redColor = withContext(Dispatchers.IO) {
                    delay(1000)
                    getColor("#ff0000")

                }
                change_ui.setBackgroundColor(redColor)
                val greenColor = withContext(Dispatchers.IO) {
                    delay(1000)
                    getColor("#00ff00")
                }
                change_ui.setBackgroundColor(greenColor)
            }
        }
    }
}

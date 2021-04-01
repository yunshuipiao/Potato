package com.swensun.func.livedata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.swensun.func.KvStore
import com.swensun.potato.R
import com.swensun.swutils.util.Logger
import kotlinx.android.synthetic.main.live_data_fragment.*


class LiveDataFragment : Fragment() {

    companion object {
        fun newInstance() = LiveDataFragment()
    }


    private lateinit var viewModel: LiveDataViewModel
    private var count = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.live_data_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LiveDataViewModel::class.java)
        KvStore.liveData<S>("sw", false).observe(viewLifecycleOwner, Observer {
            Logger.d("kvstore liveData sw $it")
        })
        btn_livedata.setOnClickListener {
            count += 1
            if (count % 2 == 1) {
                KvStore.set("sw", S(n = "sw", l = arrayListOf(1, 2, 3)))
            } else {
                val value = KvStore.get("sw", S())
                Logger.d("kvstore, get value: $value")
            }
        }
    }
}

data class S(var n: String = "", var l: List<Int> = arrayListOf(2, 3, 4))
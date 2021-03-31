package com.swensun.func.utilcode

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.swensun.potato.R
import kotlinx.android.synthetic.main.util_code_fragment.*

class UtilCodeFragment : Fragment() {

    companion object {
        fun newInstance() = UtilCodeFragment()
        const val notification_id = 0x0001
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.util_code_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()                       
    }

    private fun initView() {
        btn_test.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("potato://potato/push?name=potato"))
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
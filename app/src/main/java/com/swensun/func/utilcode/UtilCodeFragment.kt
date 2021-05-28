package com.swensun.func.utilcode

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.swensun.base.BaseFragment
import com.swensun.potato.databinding.UtilCodeFragmentBinding

class UtilCodeFragment : BaseFragment<UtilCodeFragmentBinding>() {

    companion object {
        fun newInstance() = UtilCodeFragment()
        const val notification_id = 0x0001
    }
    
    override fun initView(savedInstanceState: Bundle?) {
        binding.btnTest.setOnClickListener {
            try {
                val intent =
                    Intent(Intent.ACTION_VIEW, Uri.parse("potato://potato/push?name=potato"))
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
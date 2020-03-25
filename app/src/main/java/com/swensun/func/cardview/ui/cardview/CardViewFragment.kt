package com.swensun.func.cardview.ui.cardview

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.swensun.potato.R
import com.swensun.potato.databinding.CardViewFragmentBinding
import com.swensun.swutils.ui.setImageSrc
import kotlinx.android.synthetic.main.card_view_fragment.*

class CardViewFragment : Fragment() {

    companion object {
        fun newInstance() = CardViewFragment()
    }


    lateinit var mBinding: CardViewFragmentBinding
    private lateinit var viewModel: CardViewViewModel
    private var index = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = CardViewFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CardViewViewModel::class.java)
        initView()
    }

    private fun initView() {
        mBinding.root.setOnClickListener {
            changeView()
        }
    }

    fun changeView() {
        if (index % 2 == 0) {
            mBinding.image1.setImageSrc(R.drawable.ic_series)
            mBinding.image2.setImageSrc(R.drawable.ic_movie)
        } else {
            mBinding.image1.setImageSrc("http://assets.zvod.badambiz.com/thumb_330x440_1574247809210.jpg")
            mBinding.image2.setImageSrc("http://assets.zvod.badambiz.com/thumb_X3封面_1575019636553.jpg")
        }
        index += 1
    }
}

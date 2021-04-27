package com.swensun.func.customview

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.swensun.potato.databinding.CustomViewFragmentBinding
import com.swensun.swutils.ui.setHeight
import com.swensun.swutils.ui.setWidth

class CustomViewFragment : Fragment() {

    companion object {
        fun newInstance() = CustomViewFragment()
    }

    private lateinit var viewModel: CustomViewViewModel
    private lateinit var binding: CustomViewFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CustomViewFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CustomViewViewModel::class.java)

        val url = "https://assets-adnet.badambiz.com/adnet_2021427X154034_1619509247268.jpeg"

        Glide.with(this).asBitmap().load(url).into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                val width = resource.width
                val height = resource.height
                binding.ivCover.setWidth(width)
                binding.ivCover.setHeight(height)
                binding.ivCover.setImageBitmap(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {
            }
        })
    }
}

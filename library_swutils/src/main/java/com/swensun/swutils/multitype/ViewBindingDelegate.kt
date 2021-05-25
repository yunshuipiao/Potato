package com.ziipin.social.base.multitype

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.drakeet.multitype.ItemViewDelegate

/**
 * author : zp
 * date : 2021/5/21
 * xjfad_branch
 */
abstract class ViewBindingDelegate<T, VB : ViewBinding> :
    ItemViewDelegate<T, ViewBindingViewHolder<VB>>() {

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup
    ): ViewBindingViewHolder<VB> {
        return ViewBindingViewHolder(inflateBindingWithGeneric(parent))
    }
}

class ViewBindingViewHolder<VB : ViewBinding>(val binding: VB) :
    RecyclerView.ViewHolder(binding.root)
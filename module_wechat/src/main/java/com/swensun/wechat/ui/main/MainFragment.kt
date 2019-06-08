package com.swensun.wechat.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.swensun.wechat.R
import com.swensun.wechat.repository.proto.Comment
import com.swensun.wechat.repository.proto.Image
import com.swensun.wechat.repository.proto.UserTweetRes
import com.swensun.wechat.setCircularImage
import com.swensun.wechat.setImage
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    val adapter = Adapter()

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        initView()
        bindData()
    }

    private fun bindData() {
        viewModel.tweetsLiveData.observe(this, Observer {
            if (it.size < 5) {
                viewModel.hasMoreTweets = false
            }
            adapter.setItemList(it)
        })
        viewModel.userInfoLiveData.observe(this, Observer {
            tv_user_name.text = it.userName
            iv_user_bg.setImage(it.profileImage)
            iv_user_avatar.setImage(it.avatar)
        })
        viewModel.requestUserInfo()
        viewModel.requestUserTweetsFirstPage()
    }

    private fun initView() {
        recycler.layoutManager = LinearLayoutManager(requireActivity())
        recycler.adapter = adapter
    }

    inner class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

        val itemList = arrayListOf<UserTweetRes>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wechat, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return itemList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (position == itemList.size - 2) {
                viewModel.requestUserTweetsNextPage(itemList.size)
            }
            holder.bindData(position)
        }

        fun setItemList(list: List<UserTweetRes>?) {
            if (list == null) {
                itemList.clear()
            } else {
                itemList.addAll(list)
            }
            notifyDataSetChanged()
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val imageView = itemView.findViewById<ImageView>(R.id.iv_avatar)
            val nameView = itemView.findViewById<TextView>(R.id.tv_name)
            val contentView = itemView.findViewById<TextView>(R.id.tv_content)
            val imageRecyclerView = itemView.findViewById<RecyclerView>(R.id.image_recycler)
            val commentRecyclerView = itemView.findViewById<RecyclerView>(R.id.comment_recycler)

            fun bindData(position: Int) {
                imageView.setCircularImage(itemList[position].sender.avatar, 10)
                val sender = itemList[position].sender
                val showName = if (sender.nick.isNotBlank()) sender.nick else sender.username
                nameView.text = showName

                val content = itemList[position].content
                contentView.text = content
                val images = itemList[position].images
                if (images.isEmpty()) {
                    imageRecyclerView.visibility = View.GONE
                } else {
                    imageRecyclerView.visibility = View.VISIBLE
                    imageRecyclerView.layoutManager = GridLayoutManager(itemView.context, 3)
                    val adapter = ImageAdapter()
                    imageRecyclerView.adapter = adapter
                    adapter.setItemList(images)
                }

                val comments = itemList[position].comments
                if (comments.isEmpty()) {
                    commentRecyclerView.visibility = View.GONE
                } else {
                    commentRecyclerView.visibility = View.VISIBLE
                    commentRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
                    val commentAdapter = CommentAdapter()
                    commentRecyclerView.adapter = commentAdapter
                    commentAdapter.setItemList(comments)
                }
            }
        }
    }

    class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

        val itemList = arrayListOf<Image>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
            return ImageViewHolder(view)
        }

        override fun getItemCount(): Int {
            return itemList.size
        }

        override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
            holder.bindData(position)
        }

        fun setItemList(images: List<Image>) {
            itemList.clear()
            itemList.addAll(images)
            notifyDataSetChanged()
        }

        inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageView = itemView.findViewById<ImageView>(R.id.iv_image)
            fun bindData(position: Int) {
                imageView.setImage(itemList[position].url)
            }
        }
    }

    class CommentAdapter : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

        val itemList = arrayListOf<Comment>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
            return CommentViewHolder(view)
        }

        override fun getItemCount(): Int {
            return itemList.size
        }

        override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
            holder.bindData(position)
        }


        fun setItemList(comments: List<Comment>) {
            itemList.clear()
            itemList.addAll(comments)
            notifyDataSetChanged()
        }

        inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val commentView = itemView.findViewById<TextView>(R.id.tv_comment)
            fun bindData(position: Int) {
                val comment = itemList[position]
                val content = "${comment.sender.nick}: ${comment.content}"
                commentView.text = content
            }
        }
    }
}





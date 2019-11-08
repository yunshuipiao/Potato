package com.swensun.music.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.media.MediaMetadataCompat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder
import com.swensun.music.R
import kotlinx.android.synthetic.main.main_fragment.*
import org.w3c.dom.Text
import com.chad.library.adapter.base.BaseQuickAdapter as BaseQuickAdapter

class MainFragment : Fragment() {

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
        viewModel.init(requireContext())

        mf_to_previous.setOnClickListener {
            viewModel.skipToPrevious()
        }
        mf_to_next.setOnClickListener {
            viewModel.skipToNext()
        }
        mf_to_play.setOnClickListener {
            viewModel.playOrPause()
        }
        mf_tv_seek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.seekTo(progress)
            }
        })
    }

    class MusicAdapter: RecyclerView.Adapter<MusicViewHolder>() {
        private var mList = arrayListOf<MediaMetadataCompat>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.item_music, parent, false)
            return MusicViewHolder(view)
        }

        override fun getItemCount(): Int {
            return mList.size
        }

        override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
            holder.update(mList[position])
        }

        public fun setList(datas: List<MediaMetadataCompat>) {
            mList.clear()
            mList.addAll(datas)
            notifyDataSetChanged()
        }
    }

    class MusicViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private var titleView = itemView.findViewById<TextView>(R.id.im_tv_title)
        private var singerView = itemView.findViewById<TextView>(R.id.im_tv_singer)

        public fun update(data: MediaMetadataCompat) {
            titleView.text = data.getString(MediaMetadataCompat.METADATA_KEY_TITLE)
            singerView.text = data.getString(MediaMetadataCompat.METADATA_KEY_ARTIST)
        }
    }
}

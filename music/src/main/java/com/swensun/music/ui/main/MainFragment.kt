package com.swensun.music.ui.main

import android.os.Bundle
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.swensun.music.R
import kotlinx.android.synthetic.main.main_fragment.*
import org.jetbrains.anko.textColor

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val mMusicAdapter: MusicAdapter = MusicAdapter()
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
        viewModel.mPlayStateLiveData.observe(this, Observer {
            if (it.state == PlaybackStateCompat.STATE_PLAYING) {
                mf_to_play.text = "暂停"
            } else {
                mf_to_play.text = "播放"
            }
        })
        viewModel.mMetaDataLiveData.observe(this, Observer {
            val title = it.getString(MediaMetadataCompat.METADATA_KEY_TITLE)
            val singer = it.getString(MediaMetadataCompat.METADATA_KEY_ARTIST)
            val duration = it.getLong(MediaMetadataCompat.METADATA_KEY_DURATION)
            val durationShow = "${duration / 60000}: ${duration / 1000 % 60}"
            mf_tv_title.text = "标题：$title"
            mf_tv_singer.text = "歌手：$singer"
            mf_tv_progress.text = "时长：$durationShow"
            mMusicAdapter.notifyPlayingMusic(it.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID))
        })
        viewModel.mMusicsLiveData.observe(this, Observer {
            mMusicAdapter.setList(it)
        })
        mf_to_previous.setOnClickListener {
            viewModel.skipToPrevious()
        }
        mf_to_next.setOnClickListener {
            viewModel.skipToNext()
        }
        mf_to_play.setOnClickListener {
            viewModel.playOrPause()
        }
        mf_to_load.setOnClickListener {
            viewModel.getNetworkPlayList()
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
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.setHasFixedSize(true)
        recycler.adapter = mMusicAdapter
    }

    inner class MusicAdapter: RecyclerView.Adapter<MusicViewHolder>() {
        private var mList = arrayListOf<MediaDescriptionCompat>()
        private var mPlayingMusicId = ""
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_music, parent, false)
            return MusicViewHolder(view)
        }

        override fun getItemCount(): Int {
            return mList.size
        }

        override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
            holder.update(mList[position], mPlayingMusicId)
        }

        public fun setList(datas: List<MediaDescriptionCompat>) {
            mList.clear()
            mList.addAll(datas)
            notifyDataSetChanged()
        }

        fun notifyPlayingMusic(mediaId: String) {
            mPlayingMusicId = mediaId
            notifyDataSetChanged()
        }
    }

    inner class MusicViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private var titleView = itemView.findViewById<TextView>(R.id.im_tv_title)
        private var singerView = itemView.findViewById<TextView>(R.id.im_tv_singer)

        public fun update(
            data: MediaDescriptionCompat,
            mPlayingMusicId: String
        ) {
            titleView.text = data.title
            singerView.text = data.subtitle
            if (mPlayingMusicId == data.mediaId) {
                titleView.textColor = ContextCompat.getColor(itemView.context, R.color.colorPrimary)
                singerView.textColor = ContextCompat.getColor(itemView.context, R.color.colorPrimary)
            } else {
                titleView.textColor = ContextCompat.getColor(itemView.context, R.color.black)
                singerView.textColor = ContextCompat.getColor(itemView.context, R.color.black)
            }
            itemView.setOnClickListener {
                viewModel.playFromMediaId(data.mediaId ?: "")
            }
        }
    }
}

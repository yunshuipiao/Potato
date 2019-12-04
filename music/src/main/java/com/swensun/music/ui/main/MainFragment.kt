package com.swensun.music.ui.main

import android.os.*
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
import com.swensun.music.MusicHelper
import com.swensun.music.R
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.delay
import org.jetbrains.anko.textColor

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var mPlayState: PlaybackStateCompat = PlaybackStateCompat.Builder().build()
    private val mMusicAdapter: MusicAdapter = MusicAdapter()
    private lateinit var viewModel: MainViewModel
    private var handler = SeekHandle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onResume() {
        super.onResume()
        viewModel.init(requireContext())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.mPlayStateLiveData.observe(this, Observer {
            if (it.state == PlaybackStateCompat.STATE_PLAYING) {
                mf_to_play.text = "暂停"
                mPlayState = it
                mf_tv_seek.progress = it.position.toInt()
                handler.sendEmptyMessageDelayed(1, 250)

            } else {
                mf_to_play.text = "播放"
                handler.removeMessages(1)

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
            mf_tv_seek.max = duration.toInt()
        })
        viewModel.mMusicsLiveData.observe(this, Observer {
            mMusicAdapter.setList(it)
        })
        /**
         * 上一首
         */
        mf_to_previous.setOnClickListener {
            viewModel.init(requireContext())
        }
        /**
         * 下一首
         */
        mf_to_next.setOnClickListener {
            viewModel.skipToNext()

        }
        /**
         * 播放暂停
         */
        mf_to_play.setOnClickListener {
            viewModel.playOrPause()
        }
        /**
         * 加载音乐
         */
        mf_to_load.setOnClickListener {
            viewModel.getNetworkPlayList()
        }
        mf_tv_seek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                handler.removeMessages(1)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                viewModel.seekTo(seekBar?.progress ?: 0)
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

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

    inner class SeekHandle: Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            var position = (SystemClock.elapsedRealtime() - mPlayState.lastPositionUpdateTime ) * mPlayState.playbackSpeed + mPlayState.position
            mf_tv_seek.progress = position.toInt()
            sendEmptyMessageDelayed(1, 250)
        }
    }

    override fun onStop() {
        super.onStop()
        handler.removeMessages(1)
    }
}

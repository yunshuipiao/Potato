package com.swensun.music.ui.main

import MusicLibrary
import android.content.ComponentName
import android.content.Context
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swensun.music.MusicHelper
import com.swensun.music.service.MusicService

class MainViewModel : ViewModel() {

    private lateinit var mContext: Context
    /**
     * 播放控制器，对 Service 发出播放，暂停，上下一曲的指令
     */
    private lateinit var mMediaControllerCompat: MediaControllerCompat
    /**
     * 媒体浏览器，负责连接 Service，得到 Service 的相关信息
     */
    private lateinit var mMediaBrowserCompat: MediaBrowserCompat
    /**
     * 播放状态的数据(是否正在播放，播放进度)
     */
    public var mPlayStateLiveData = MutableLiveData<PlaybackStateCompat>()
    /**
     * 播放歌曲的数据（歌曲，歌手等）
     */
    public var mMetaDataLiveData = MutableLiveData<MediaMetadataCompat>()
    /**
     * 播放列表的数据
     */
    public var mMusicsLiveData = MutableLiveData<MutableList<MediaDescriptionCompat>>()
    /**
     * 播放控制器的回调
     * （比如 UI 发出下一曲指令，Service 端切换歌曲播放之后，将播放状态信息传回 UI 端， 更新 UI）
     */
    private var mMediaControllerCompatCallback = object : MediaControllerCompat.Callback() {
        override fun onQueueChanged(queue: MutableList<MediaSessionCompat.QueueItem>?) {
            super.onQueueChanged(queue)
            // 服务端的queue变化
            MusicHelper.log("onQueueChanged: $queue" )
            mMusicsLiveData.postValue(queue?.map { it.description } as MutableList<MediaDescriptionCompat>)

        }

        override fun onRepeatModeChanged(repeatMode: Int) {
            super.onRepeatModeChanged(repeatMode)
        }

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            super.onPlaybackStateChanged(state)
            mPlayStateLiveData.postValue(state)
            MusicHelper.log("music onPlaybackStateChanged, $state")
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            super.onMetadataChanged(metadata)
            MusicHelper.log("onMetadataChanged, $metadata")
            mMetaDataLiveData.postValue(metadata)
        }

        override fun onSessionReady() {
            super.onSessionReady()
        }

        override fun onSessionDestroyed() {
            super.onSessionDestroyed()
        }

        override fun onAudioInfoChanged(info: MediaControllerCompat.PlaybackInfo?) {
            super.onAudioInfoChanged(info)
        }
    }

    /**
     * 媒体浏览器连接 Service 的回调
     */
    private var mMediaBrowserCompatConnectionCallback: MediaBrowserCompat.ConnectionCallback = object :
        MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            super.onConnected()
            // 连接成功
            MusicHelper.log("onConnected")
            mMediaControllerCompat = MediaControllerCompat(mContext, mMediaBrowserCompat.sessionToken)
            mMediaControllerCompat.registerCallback(mMediaControllerCompatCallback)
            mMediaBrowserCompat.subscribe(mMediaBrowserCompat.root, mMediaBrowserCompatSubscriptionCallback)
            mMediaControllerCompat.transportControls.setRepeatMode(PlaybackStateCompat.REPEAT_MODE_ALL)
        }

        override fun onConnectionSuspended() {
            super.onConnectionSuspended()
        }

        override fun onConnectionFailed() {
            super.onConnectionFailed()
        }
    }

    /**
     * 媒体浏览器订阅 Service 数据的回调
     */
    private var mMediaBrowserCompatSubscriptionCallback = object : MediaBrowserCompat.SubscriptionCallback() {
        override fun onChildrenLoaded(
            parentId: String,
            children: MutableList<MediaBrowserCompat.MediaItem>
        ) {
            super.onChildrenLoaded(parentId, children)
            // 服务器 setChildLoad 的回调方法
            MusicHelper.log("onChildrenLoaded, $children")

        }
    }
    fun init(context: Context) {
        mContext = context
        mMediaBrowserCompat = MediaBrowserCompat(context, ComponentName(context, MusicService::class.java),
            mMediaBrowserCompatConnectionCallback, null)
        mMediaBrowserCompat.connect()
    }

    override fun onCleared() {
        super.onCleared()
        mMediaControllerCompat.unregisterCallback(mMediaControllerCompatCallback)
        if (mMediaBrowserCompat.isConnected) {
            mMediaBrowserCompat.disconnect()
        }
    }

    fun skipToNext() {
        mMediaControllerCompat.transportControls.skipToNext()
    }

    fun skipToPrevious() {
        mMediaControllerCompat.transportControls.skipToPrevious()
    }

    fun playOrPause() {
        if (mPlayStateLiveData.value?.state == PlaybackStateCompat.STATE_PLAYING) {
            mMediaControllerCompat.transportControls.pause()
        } else {
            mMediaControllerCompat.transportControls.play()
        }
    }

    fun seekTo(progress: Int) {
        mMediaControllerCompat.transportControls.seekTo(progress.toLong())
    }

    fun getNetworkPlayList() {
       val playList =  MusicLibrary.getMusicList()
        playList.forEach {
            mMediaControllerCompat.addQueueItem(it.description)
        }
    }

    fun playFromMediaId(mediaId: String) {
        mMediaControllerCompat.transportControls.playFromMediaId(mediaId, null)
    }
}

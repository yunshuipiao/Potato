package com.swensun.music.ui.main

import MusicLibrary
import android.content.ComponentName
import android.content.Context
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.ViewModel
import com.swensun.music.MusicHelper
import com.swensun.music.service.MusicService

class MainViewModel : ViewModel() {

    private lateinit var mContext: Context
    private lateinit var mMediaControllerCompat: MediaControllerCompat
    private lateinit var mMediaBrowserCompat: MediaBrowserCompat
    private var mPlaying = false
    private var mMediaControllerCompatCallback = object : MediaControllerCompat.Callback() {
        override fun onQueueChanged(queue: MutableList<MediaSessionCompat.QueueItem>?) {
            super.onQueueChanged(queue)
            // 服务端的queue变化
            MusicHelper.log("onQueueChanged: $queue" )
        }

        override fun onRepeatModeChanged(repeatMode: Int) {
            super.onRepeatModeChanged(repeatMode)
        }

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            super.onPlaybackStateChanged(state)
            mPlaying = state?.state == PlaybackStateCompat.STATE_PLAYING
            MusicHelper.log("music onPlaybackStateChanged, $state")
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            super.onMetadataChanged(metadata)
            MusicHelper.log("onMetadataChanged, $metadata")
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

    private var mMediaBrowserCompatConnectionCallback: MediaBrowserCompat.ConnectionCallback = object :
        MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            super.onConnected()
            // 连接成功
            mMediaControllerCompat = MediaControllerCompat(mContext, mMediaBrowserCompat.sessionToken)
            mMediaControllerCompat.registerCallback(mMediaControllerCompatCallback)
            mMediaBrowserCompat.subscribe(mMediaBrowserCompat.root, mMediaBrowserCompatSubscriptionCallback)
        }

        override fun onConnectionSuspended() {
            super.onConnectionSuspended()
        }

        override fun onConnectionFailed() {
            super.onConnectionFailed()
        }
    }

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
        if (mPlaying) {
            mMediaControllerCompat.transportControls.pause()
        } else {
            mMediaControllerCompat.transportControls.play()
        }
    }

    fun seekTo(progress: Int) {
        mMediaControllerCompat.transportControls.seekTo(progress.toLong())
    }

    fun getNetworkPlayList() {
        MusicLibrary.getMusicList()
    }
}

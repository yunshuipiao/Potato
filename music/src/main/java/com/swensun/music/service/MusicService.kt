package com.swensun.music.service

import MusicLibrary
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.ResultReceiver
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.RatingCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.media.MediaBrowserServiceCompat
import com.swensun.music.MusicHelper

class MusicService : MediaBrowserServiceCompat() {
    private var mPlayList = arrayListOf<MediaBrowserCompat.MediaItem>()
    private var mMusicIndex = 0
    private var mCurrentMedia: MediaBrowserCompat.MediaItem? = null
    private lateinit var mSession: MediaSessionCompat


    private var mMediaPlayer: MediaPlayer = MediaPlayer()
    // 播放控制器的事件回调
    private var mSessionCallback = object : MediaSessionCompat.Callback() {
        override fun onMediaButtonEvent(mediaButtonEvent: Intent?): Boolean {
            return super.onMediaButtonEvent(mediaButtonEvent)
        }

        override fun onRewind() {
            super.onRewind()
        }

        override fun onSeekTo(pos: Long) {
            super.onSeekTo(pos)
        }

        override fun onAddQueueItem(description: MediaDescriptionCompat?) {
            super.onAddQueueItem(description)
        }

        override fun onAddQueueItem(description: MediaDescriptionCompat?, index: Int) {
            super.onAddQueueItem(description, index)
        }

        override fun onSkipToPrevious() {
            super.onSkipToPrevious()
        }

        override fun onCustomAction(action: String?, extras: Bundle?) {
            super.onCustomAction(action, extras)
        }

        override fun onPrepare() {
            super.onPrepare()
            if (mPlayList.isEmpty()) {
                MusicHelper.log("not playlist")
                return
            }
            if (mMusicIndex < 0 || mMusicIndex >= mPlayList.size) {
                MusicHelper.log("media index error")
                return
            }
            mCurrentMedia = mPlayList.get(mMusicIndex)
            var uri = mCurrentMedia?.description?.mediaUri
            MusicHelper.log("uri, $uri")
            if (uri == null) {
                return
            }
            try {
                if (uri.toString().startsWith("http")) {
                    mMediaPlayer.setDataSource(applicationContext, uri)
                } else {
                    //  assets 资源
                    val assetFileDescriptor = applicationContext.assets.openFd(uri.toString())
                    mMediaPlayer.setDataSource(
                        assetFileDescriptor.fileDescriptor,
                        assetFileDescriptor.startOffset,
                        assetFileDescriptor.length
                    )
                }

                mMediaPlayer.prepareAsync()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        override fun onFastForward() {
            super.onFastForward()
        }

        override fun onPlay() {
            super.onPlay()
            if (mCurrentMedia == null) {
                onPrepare()
            }
            mMediaPlayer.start()

        }

        override fun onStop() {
            super.onStop()
            mMediaPlayer.pause()
        }

        override fun onSkipToQueueItem(id: Long) {
            super.onSkipToQueueItem(id)
        }

        override fun onRemoveQueueItem(description: MediaDescriptionCompat?) {
            super.onRemoveQueueItem(description)
        }

        override fun onSkipToNext() {
            super.onSkipToNext()
        }

        override fun onSetPlaybackSpeed(speed: Float) {
            super.onSetPlaybackSpeed(speed)
        }

        override fun onPrepareFromMediaId(mediaId: String?, extras: Bundle?) {
            super.onPrepareFromMediaId(mediaId, extras)
        }

        override fun onSetRepeatMode(repeatMode: Int) {
            super.onSetRepeatMode(repeatMode)
        }

        override fun onCommand(command: String?, extras: Bundle?, cb: ResultReceiver?) {
            super.onCommand(command, extras, cb)
        }

        override fun onPause() {
            super.onPause()
        }

        override fun onPrepareFromSearch(query: String?, extras: Bundle?) {
            super.onPrepareFromSearch(query, extras)
        }

        override fun onPlayFromMediaId(mediaId: String?, extras: Bundle?) {
            super.onPlayFromMediaId(mediaId, extras)
        }

        override fun onSetShuffleMode(shuffleMode: Int) {
            super.onSetShuffleMode(shuffleMode)
        }

        override fun onPrepareFromUri(uri: Uri?, extras: Bundle?) {
            super.onPrepareFromUri(uri, extras)
        }

        override fun onPlayFromSearch(query: String?, extras: Bundle?) {
            super.onPlayFromSearch(query, extras)
        }

        override fun onPlayFromUri(uri: Uri?, extras: Bundle?) {
            super.onPlayFromUri(uri, extras)
        }

        override fun onSetRating(rating: RatingCompat?) {
            super.onSetRating(rating)
        }

        override fun onSetRating(rating: RatingCompat?, extras: Bundle?) {
            super.onSetRating(rating, extras)
        }

        override fun onSetCaptioningEnabled(enabled: Boolean) {
            super.onSetCaptioningEnabled(enabled)
        }
    }
    // 播放器的回调
    private var mCompletionListener: MediaPlayer.OnCompletionListener =
        MediaPlayer.OnCompletionListener {

        }
    private var mPreparedListener: MediaPlayer.OnPreparedListener =
        MediaPlayer.OnPreparedListener {
            mSessionCallback.onPlay()
        }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        MusicHelper.log("onLoadChildren, $parentId")
        result.detach()
        val playList = MusicLibrary.getMusicList()
        val queues = arrayListOf<MediaBrowserCompat.MediaItem>()
        playList.forEach {
            queues.add(MediaBrowserCompat.MediaItem(
                it.description, MediaBrowserCompat.MediaItem.FLAG_PLAYABLE))
        }
        mPlayList.addAll(queues)
        result.sendResult(mPlayList)
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        return BrowserRoot("MusicService", null)
    }

    override fun onCreate() {
        super.onCreate()
        mSession = MediaSessionCompat(applicationContext, "MusicService")
        mSession.setCallback(mSessionCallback)
        sessionToken = mSession.sessionToken
        mMediaPlayer.setOnCompletionListener(mCompletionListener)
        mMediaPlayer.setOnPreparedListener(mPreparedListener)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
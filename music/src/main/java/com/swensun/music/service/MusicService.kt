package com.swensun.music.service

import MusicLibrary
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.ResultReceiver
import android.os.SystemClock
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.RatingCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.media.MediaBrowserServiceCompat
import com.swensun.music.MusicHelper
import putDuration

class MusicService : MediaBrowserServiceCompat() {
    private var mRepeatMode: Int = PlaybackStateCompat.REPEAT_MODE_NONE
    private var mState: Int = 0
    private var mPlayList = arrayListOf<MediaSessionCompat.QueueItem>()
    private var mMusicIndex = -1
    private var mCurrentMedia: MediaSessionCompat.QueueItem? = null
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
            mMediaPlayer.seekTo(pos.toInt())
            setNewState(mState)
        }

        override fun onAddQueueItem(description: MediaDescriptionCompat) {
            super.onAddQueueItem(description)
            // 客户端添加歌曲
            if (mPlayList.find { it.description.mediaId == description.mediaId } == null) {
                mPlayList.add(
                    MediaSessionCompat.QueueItem(description, description.hashCode().toLong())
                )
            }
            mMusicIndex = if (mMusicIndex == -1) 0 else mMusicIndex
            mSession.setQueue(mPlayList)
        }

        override fun onRemoveQueueItem(description: MediaDescriptionCompat?) {
            super.onRemoveQueueItem(description)
            mPlayList.remove(MediaSessionCompat.QueueItem(description, description.hashCode().toLong()))
            mMusicIndex = if (mPlayList.isEmpty()) -1 else mMusicIndex
            mSession.setQueue(mPlayList)

        }

        override fun onSkipToPrevious() {
            super.onSkipToPrevious()
            if (mPlayList.isEmpty()) {
                MusicHelper.log("not playlist")
                return
            }
            mMusicIndex = if (mMusicIndex > 0) mMusicIndex - 1 else mPlayList.size - 1
            mCurrentMedia = null
            onPlay()
        }

        override fun onSkipToNext() {
            super.onSkipToNext()
            if (mPlayList.isEmpty()) {
                MusicHelper.log("not playlist")
                return
            }
            mMusicIndex = (++mMusicIndex % mPlayList.size)
            mCurrentMedia = null
            onPlay()
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
            mCurrentMedia = mPlayList[mMusicIndex]
            val uri = mCurrentMedia?.description?.mediaUri
            MusicHelper.log("uri, $uri")
            if (uri == null) {
                return
            }
            // 加载资源要重置
            mMediaPlayer.reset()
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
            if (mCurrentMedia == null) {
                return
            }
            mMediaPlayer.start()
            setNewState(PlaybackStateCompat.STATE_PLAYING)
        }

        override fun onPause() {
            super.onPause()
            mMediaPlayer.pause()
            setNewState(PlaybackStateCompat.STATE_PAUSED)
        }

        override fun onStop() {
            super.onStop()
            mMediaPlayer.stop()
            setNewState(PlaybackStateCompat.STATE_STOPPED)
        }

        override fun onSkipToQueueItem(id: Long) {
            super.onSkipToQueueItem(id)
        }

        override fun onSetPlaybackSpeed(speed: Float) {
            super.onSetPlaybackSpeed(speed)
        }

        override fun onPrepareFromMediaId(mediaId: String?, extras: Bundle?) {
            super.onPrepareFromMediaId(mediaId, extras)
        }

        override fun onSetRepeatMode(repeatMode: Int) {
            super.onSetRepeatMode(repeatMode)
            mRepeatMode = repeatMode
        }

        override fun onCommand(command: String?, extras: Bundle?, cb: ResultReceiver?) {
            super.onCommand(command, extras, cb)
        }

        override fun onPrepareFromSearch(query: String?, extras: Bundle?) {
            super.onPrepareFromSearch(query, extras)
        }

        override fun onPlayFromMediaId(mediaId: String?, extras: Bundle?) {
            super.onPlayFromMediaId(mediaId, extras)
            MusicHelper.log("cur mp3: ${mCurrentMedia?.description?.mediaUri}")
            if (mediaId == mCurrentMedia?.description?.mediaId) {
                // 同一首歌曲
                if (!mMediaPlayer.isPlaying) {
                    onPlay()
                    return
                }
            }
            mMusicIndex = mPlayList.indexOfFirst { it.description.mediaId == mediaId}
            mCurrentMedia = null
            onPlay()
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

    private fun setNewState(state: Int) {
        mState = state
        val stateBuilder = PlaybackStateCompat.Builder()
        stateBuilder.setActions(getAvailableActions())
        stateBuilder.setState(
            state,
            mMediaPlayer.currentPosition.toLong(),
            1.0f,
            SystemClock.elapsedRealtime()
        )
        mSession.setPlaybackState(stateBuilder.build())
    }

    // 播放器的回调
    private var mCompletionListener: MediaPlayer.OnCompletionListener =
        MediaPlayer.OnCompletionListener {
            MusicHelper.log("OnCompletionListener")
            setNewState(PlaybackStateCompat.STATE_STOPPED)
            when (mRepeatMode) {
                PlaybackStateCompat.REPEAT_MODE_ONE -> {
                    mSessionCallback.onPlay()
                }
                PlaybackStateCompat.REPEAT_MODE_ALL -> {
                    mSessionCallback.onSkipToNext()
                }
                PlaybackStateCompat.REPEAT_MODE_NONE -> {
                    if (mMusicIndex != mPlayList.size - 1) {
                        mSessionCallback.onSkipToNext()
                    }
                }
            }
        }
    private var mPreparedListener: MediaPlayer.OnPreparedListener =
        MediaPlayer.OnPreparedListener {
            val mediaId = mCurrentMedia?.description?.mediaId ?: ""
            val metadata = MusicLibrary.getMeteDataFromId(mediaId)
            mSession.setMetadata(metadata.putDuration(mMediaPlayer.duration.toLong()))
            mSessionCallback.onPlay()
        }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        MusicHelper.log("onLoadChildren, $parentId")
        result.detach()
        val list = mPlayList.map { MediaBrowserCompat.MediaItem(it.description, MediaBrowserCompat.MediaItem.FLAG_PLAYABLE) }
        result.sendResult(list as MutableList<MediaBrowserCompat.MediaItem>?)
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
                mSession.setFlags(
                    MediaSessionCompat.FLAG_HANDLES_QUEUE_COMMANDS
                )
        sessionToken = mSession.sessionToken
        mMediaPlayer.setOnCompletionListener(mCompletionListener)
        mMediaPlayer.setOnPreparedListener(mPreparedListener)
        mMediaPlayer.setOnErrorListener { mp, what, extra ->
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @PlaybackStateCompat.Actions
    private fun getAvailableActions(): Long {
        var actions = (PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID
                or PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH
                or PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                or PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS)
        actions = when (mState) {
            PlaybackStateCompat.STATE_STOPPED -> actions or (PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PAUSE)
            PlaybackStateCompat.STATE_PLAYING -> actions or (PlaybackStateCompat.ACTION_STOP
                    or PlaybackStateCompat.ACTION_PAUSE
                    or PlaybackStateCompat.ACTION_SEEK_TO)
            PlaybackStateCompat.STATE_PAUSED -> actions or (PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_STOP)
            else -> actions or (PlaybackStateCompat.ACTION_PLAY
                    or PlaybackStateCompat.ACTION_PLAY_PAUSE
                    or PlaybackStateCompat.ACTION_STOP
                    or PlaybackStateCompat.ACTION_PAUSE)
        }
        return actions
    }
}
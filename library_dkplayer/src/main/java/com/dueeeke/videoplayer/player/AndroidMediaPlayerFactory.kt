package com.dueeeke.videoplayer.player

import android.content.Context

/**
 * 创建[AndroidMediaPlayer]的工厂类，不推荐，系统的MediaPlayer兼容性较差，建议使用IjkPlayer或者ExoPlayer
 */
class AndroidMediaPlayerFactory : PlayerFactory<AndroidMediaPlayer>() {
    override fun createPlayer(context: Context?): AndroidMediaPlayer {
        return AndroidMediaPlayer(context)
    }

    companion object {
        fun create(): AndroidMediaPlayerFactory {
            return AndroidMediaPlayerFactory()
        }
    }
}
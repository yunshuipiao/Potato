package com.dueeeke.videoplayer.exo

import android.content.Context
import com.dueeeke.videoplayer.player.PlayerFactory

class ExoMediaPlayerFactory : PlayerFactory<ExoMediaPlayer>() {
    override fun createPlayer(context: Context): ExoMediaPlayer {
        return ExoMediaPlayer(context)
    }

    companion object {
        fun create(): ExoMediaPlayerFactory {
            return ExoMediaPlayerFactory()
        }
    }
}
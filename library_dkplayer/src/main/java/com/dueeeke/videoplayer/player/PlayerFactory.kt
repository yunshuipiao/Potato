package com.dueeeke.videoplayer.player

import android.content.Context

/**
 * 此接口使用方法：
 * 1.继承[AbstractPlayer]扩展自己的播放器。
 * 2.继承此接口并实现[.createPlayer]，返回步骤1中的播放器。
 * 可参照[AndroidMediaPlayer]和[AndroidMediaPlayerFactory]的实现。
 */
abstract class PlayerFactory<P : AbstractPlayer> {
    abstract fun createPlayer(context: Context): P
}
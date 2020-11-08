package com.dueeeke.videoplayer.player

import com.dueeeke.videoplayer.exo.ExoMediaPlayerFactory
import com.dueeeke.videoplayer.render.RenderViewFactory
import com.dueeeke.videoplayer.render.TextureRenderViewFactory


class VideoViewConfig {
    var playOnMobileNetWork = false
    var enableOrientation = true
    var enableAudioFocus = true
    var isEnableLog = false
    var progressManager: ProgressManager? = null
    var playerFactory: PlayerFactory<*>? = ExoMediaPlayerFactory.create()
    var screenScaleType = 0
    var renderViewFactory: RenderViewFactory? = TextureRenderViewFactory.create()
    var adaptCutout = true
}
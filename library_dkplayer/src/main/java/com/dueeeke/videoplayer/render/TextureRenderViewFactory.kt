package com.dueeeke.videoplayer.render

import android.content.Context

class TextureRenderViewFactory : RenderViewFactory() {
    override fun createRenderView(context: Context?): IRenderView? {
        return TextureRenderView(context)
    }

    companion object {
        fun create(): TextureRenderViewFactory {
            return TextureRenderViewFactory()
        }
    }
}
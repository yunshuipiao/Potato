package com.dueeeke.videoplayer.render

import android.content.Context

/**
 * 此接口用于扩展自己的渲染View。使用方法如下：
 * 1.继承IRenderView实现自己的渲染View。
 * 2.重写createRenderView返回步骤1的渲染View。
 * 可参考[TextureRenderView]和[TextureRenderViewFactory]的实现。
 */
abstract class RenderViewFactory {
    abstract fun createRenderView(context: Context?): IRenderView?
}
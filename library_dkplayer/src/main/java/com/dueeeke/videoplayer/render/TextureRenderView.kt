package com.dueeeke.videoplayer.render

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.SurfaceTexture
import android.view.Surface
import android.view.TextureView
import android.view.TextureView.SurfaceTextureListener
import android.view.View
import com.dueeeke.videoplayer.player.AbstractPlayer

@SuppressLint("ViewConstructor")
class TextureRenderView(context: Context?) : TextureView(context), IRenderView,
    SurfaceTextureListener {
    private var mMeasureHelper: MeasureHelper? = null
    private var mSurfaceTexture: SurfaceTexture? = null
    private var mMediaPlayer: AbstractPlayer? = null
    private var mSurface: Surface? = null
    override fun attachToPlayer(player: AbstractPlayer) {
        mMediaPlayer = player
    }

    override fun setVideoSize(videoWidth: Int, videoHeight: Int) {
        if (videoWidth > 0 && videoHeight > 0) {
            mMeasureHelper?.setVideoSize(videoWidth, videoHeight)
            requestLayout()
        }
    }

    override fun setVideoRotation(degree: Int) {
        mMeasureHelper?.setVideoRotation(degree)
        rotation = degree.toFloat()
    }

    override fun setScaleType(scaleType: Int) {
        mMeasureHelper?.setScreenScale(scaleType)
        requestLayout()
    }

    override val view: View
        get() = this

    override fun doScreenShot(): Bitmap? {
        return bitmap
    }

    override fun release() {
        if (mSurface != null) mSurface?.release()
        if (mSurfaceTexture != null) mSurfaceTexture?.release()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val measuredSize = mMeasureHelper?.doMeasure(widthMeasureSpec, heightMeasureSpec)
        measuredSize?.let {
            setMeasuredDimension(measuredSize[0], measuredSize[1])
        }
    }

    override fun onSurfaceTextureAvailable(
        surfaceTexture: SurfaceTexture,
        width: Int,
        height: Int
    ) {
        if (mSurfaceTexture != null) {
            setSurfaceTexture(mSurfaceTexture)
        } else {
            mSurfaceTexture = surfaceTexture
            mSurface = Surface(surfaceTexture)
            if (mMediaPlayer != null) {
                mMediaPlayer?.setSurface(mSurface)
            }
        }
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {}
    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        return false
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}

    init {
        mMeasureHelper = MeasureHelper()
        surfaceTextureListener = this
    }
}
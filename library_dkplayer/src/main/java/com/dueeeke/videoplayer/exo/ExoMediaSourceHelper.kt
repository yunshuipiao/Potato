package com.dueeeke.videoplayer.exo

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSourceFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.upstream.cache.*
import com.google.android.exoplayer2.util.Util
import java.io.File


object ExoMediaSourceHelper {

    lateinit var appContext: Context

    private var userAgent = ""
    private var httpDataSourceFactory: HttpDataSource.Factory? = null
    var cache: Cache? = null

    fun getMediaSource(
        uri: String,
        headers: Map<String?, String?>? = null,
        isCache: Boolean = false
    ): MediaSource {
        val contentUri = Uri.parse(uri)
        if ("rtmp" == contentUri.scheme) {
            return ProgressiveMediaSource.Factory(RtmpDataSourceFactory(null))
                .createMediaSource(contentUri)
        }
        val contentType = inferContentType(uri)
        val factory: DataSource.Factory
        if (isCache) {
            factory = getCacheDataSourceFactory()
        } else {
            factory = getDataSourceFactory()
        }
        if (httpDataSourceFactory != null) {
            setHeaders(headers)
        }
        return when (contentType) {
            C.TYPE_DASH -> DashMediaSource.Factory(factory).createMediaSource(contentUri)
            C.TYPE_SS -> SsMediaSource.Factory(factory).createMediaSource(contentUri)
            C.TYPE_HLS -> HlsMediaSource.Factory(factory).createMediaSource(contentUri)
            C.TYPE_OTHER -> ProgressiveMediaSource.Factory(factory).createMediaSource(contentUri)
            else -> ProgressiveMediaSource.Factory(factory).createMediaSource(contentUri)
        }
    }

    private fun inferContentType(fileName: String): Int {
        val newFileName = Util.toLowerInvariant(fileName)
        return if (newFileName.contains(".mpd")) {
            C.TYPE_DASH
        } else if (newFileName.contains(".m3u8")) {
            C.TYPE_HLS
        } else if (newFileName.matches(Regex(".*\\.ism(l)?(/manifest(\\(.+\\))?)?"))) {
            C.TYPE_SS
        } else {
            C.TYPE_OTHER
        }
    }

    private fun getCacheDataSourceFactory(): DataSource.Factory {
        if (cache == null) {
            cache = newCache()
        }
        return CacheDataSourceFactory(
            cache,
            getDataSourceFactory(),
            CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR
        )
    }

    private fun newCache(): Cache {
        return SimpleCache(
            File(appContext.externalCacheDir, "exo-video-cache"),
            LeastRecentlyUsedCacheEvictor(10 * 1024 * 1024), //缓存
            ExoDatabaseProvider(appContext)
        )
    }

    /**
     * Returns a new DataSource factory.
     *
     * @return A new DataSource factory.
     */
    private fun getDataSourceFactory(): DataSource.Factory {
        return DefaultDataSourceFactory(appContext, getHttpDataSourceFactory())
    }

    /**
     * Returns a new HttpDataSource factory.
     *
     * @return A new HttpDataSource factory.
     */
    private fun getHttpDataSourceFactory(): DataSource.Factory? {
        if (httpDataSourceFactory == null) {
            httpDataSourceFactory = DefaultHttpDataSourceFactory(
                userAgent,
                null,
                DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,  //http->https重定向支持
                true
            )
        }
        return httpDataSourceFactory
    }

    private fun setHeaders(headers: Map<String?, String?>?) {
        headers?.keys?.forEach {
            //如果发现用户通过header传递了UA，则强行将HttpDataSourceFactory里面的userAgent字段替换成用户的
            val value = headers[it]
            if ("User-Agent" == it) {
                if (value?.isNotBlank() == true) {
                    try {
                        val userAgentFiled =
                            httpDataSourceFactory?.javaClass?.getDeclaredField("userAgent")
                        userAgentFiled?.isAccessible = true
                        userAgentFiled?.set(httpDataSourceFactory, value)
                    } catch (e: Exception) {
                        //ignore
                    }
                }
            } else {
                httpDataSourceFactory?.defaultRequestProperties?.set(it, value)
            }
        }
    }

    fun init(context: Context) {
        appContext = context
        userAgent = Util.getUserAgent(appContext, appContext.applicationInfo.name)
    }
}
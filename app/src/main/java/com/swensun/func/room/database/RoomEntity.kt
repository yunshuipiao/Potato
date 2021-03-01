package com.swensun.func.room.database

import androidx.room.*
import com.blankj.utilcode.util.GsonUtils
import com.google.gson.annotations.SerializedName
import com.swensun.swutils.util.Logger
import java.io.Serializable

@Entity
class RoomEntity {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    var title: String = ""

    var count: Int = 0

    var startTime: Int = 0

    @ColumnInfo(name = "video_detail_info_item")
    var videoInfoItem = VideoInfoItem()
}

class VideoDetailInfoItemConverter {
    @TypeConverter
    fun stringToVideoDetail(str: String): VideoInfoItem {
        try {
            Logger.d("room stringToVideoDetail")
            val video =
                GsonUtils.getGson().fromJson<VideoInfoItem>(str, VideoInfoItem::class.java)
            return video
        } catch (e: Throwable) {

        }
        return VideoInfoItem()
    }

    @TypeConverter
    fun videoDetailToString(video: VideoInfoItem): String {
        Logger.d("room videoDetailToString")
        return GsonUtils.getGson().toJson(video)
    }
}

open class VideoInfoItem : Serializable {
    /**
     * 视频id
     */
    @SerializedName("id")
    var id = 0

    /**
     * 视频名
     */
    @SerializedName("name")
    var name = ""

    /**
     * 视频中文名
     */
    @SerializedName("chinese_name")
    var chineseName = ""

    /**
     * 视频描述
     */
    @SerializedName("desc")
    var desc = ""

    /**
     * 视频icon
     */
    @SerializedName("icon")
    var icon = ""

    /**
     * 展示图片
     */
    @SerializedName("picture")
    var picture = ""

    /**
     * 视频类型id
     */
    @SerializedName("video_type_id")
    var videoTypeId = 0

    /**
     * 视频类型名
     */
    @SerializedName("video_type_name")
    var videoTypeName = ""

    /**
     * 地区id
     */
    @SerializedName("area_id")
    var areaId = 0

    /**
     * 地区名
     */
    @SerializedName("area_name")
    var areaName = ""

    /**
     * 小类型id
     */
    @SerializedName("category_id")
    var categoryId = 0

    /**
     * 小类型名字
     */
    @SerializedName("category_name")
    var categoryName = ""

    /**
     * 导演名
     */
    @SerializedName("video_writer_uyghur")
    var videoWriterUyghur = ""

    /**
     * 主演
     */
    @SerializedName("co_star_uyghur")
    var coStarUyghur = ""

    /**
     * 当前更新的总集数
     */
    @SerializedName("total_episode")
    var totalEpisode = 0

    /**
     * 该视频的总集数
     */
    @SerializedName("total_episode_count")
    var totalEpisodeCount = 0

    /**
     * 是否可以试看
     */
    @SerializedName("is_can_trial")
    var isCanTrial = false

    /**
     * 是否vip
     */
    @SerializedName("is_vip")
    var isVip = false

    /**
     * 更多集数的文案
     */
    @SerializedName("m_e_text")
    var mEText: String? = null

    /**
     * 年份
     */
    @SerializedName("year")
    var year: Int? = null

    /**
     * 时长单位秒
     */
    @SerializedName("duration")
    var duration = 0

    /**
     * 主题
     */
    @SerializedName("custom_tag")
    var customTag: String? = null

    /**
     * 热度
     */
    @SerializedName("hot")
    var hot = 0

}


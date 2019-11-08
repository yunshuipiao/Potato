import android.support.v4.media.MediaMetadataCompat

object MusicLibrary {
    private var playList = arrayListOf<MediaMetadataCompat>()

    fun getMusicList(): ArrayList<MediaMetadataCompat> {
        playList.clear()
        val music1 = MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, "35625821")
            .putString(
                MediaMetadataCompat.METADATA_KEY_MEDIA_URI,
                "http://music.163.com/song/media/outer/url?id=35625821.mp3"
            )
            .putString(MediaMetadataCompat.METADATA_KEY_TITLE, "记念")
            .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, "雷雨心")
            .putString(
                MediaMetadataCompat.METADATA_KEY_ART_URI,
                "http://p2.music.126.net/W_srVOtG_DKS1-txPLqNQQ==/3273246117001205.jpg?param=130y130"
            )
            .build()
        val music2 = MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, "27551005")
            .putString(
                MediaMetadataCompat.METADATA_KEY_MEDIA_URI,
                "http://music.163.com/song/media/outer/url?id=27551005.mp3"
            )
            .putString(MediaMetadataCompat.METADATA_KEY_TITLE, "我以为")
            .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, "王浩燃")
            .putString(
                MediaMetadataCompat.METADATA_KEY_ART_URI,
                "http://p2.music.126.net/FQE90NipEHsjmjfukgOFOA==/5753744348210261.jpg?param=130y130"
            )
            .build()
        val music3 = MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, "1379410256")
            .putString(
                MediaMetadataCompat.METADATA_KEY_MEDIA_URI,
                "http://music.163.com/song/media/outer/url?id=1379410256.mp3"
            )
            .putString(MediaMetadataCompat.METADATA_KEY_TITLE, "今后我与自己流浪")
            .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, "张碧晨")
            .putString(
                MediaMetadataCompat.METADATA_KEY_ART_URI,
                "http://p1.music.126.net/PmclXMHHh02RM0nRPUICvw==/109951164230876988.jpg?param=130y130"
            )
            .build()
        val music4 = MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, "413812448")
            .putString(
                MediaMetadataCompat.METADATA_KEY_MEDIA_URI,
                "http://music.163.com/song/media/outer/url?id=413812448.mp3"
            )
            .putString(MediaMetadataCompat.METADATA_KEY_TITLE, "大鱼")
            .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, "周深")
            .putString(
                MediaMetadataCompat.METADATA_KEY_ART_URI,
                "http://p1.music.126.net/aiPQXP8mdLovQSrKsM3hMQ==/1416170985079958.jpg?param=130y130"
            )
            .build()
        val music5 = MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, "1378085345")
            .putString(
                MediaMetadataCompat.METADATA_KEY_MEDIA_URI,
                "http://music.163.com/song/media/outer/url?id=1378085345.mp3"
            )
            .putString(MediaMetadataCompat.METADATA_KEY_TITLE, "哪吒")
            .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, "GAI周延 / 大痒痒")
            .putString(
                MediaMetadataCompat.METADATA_KEY_ART_URI,
                "http://p2.music.126.net/vcoe8am30nalBMPvPMHLzg==/109951164215638256.jpg?param=130y130"
            )
            .build()
        playList.apply {
            addAll(arrayListOf(music1, music2, music3, music4, music5))
        }
        return playList
    }
}

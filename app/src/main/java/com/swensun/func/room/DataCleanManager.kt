package com.swensun.func.room

import android.os.Environment
import com.swensun.swutils.SwUtils
import java.io.File

/**
 * author : zp
 * date : 2020/9/26
 * Potato
 */

/** * 本应用数据清除管理器  */
object DataCleanManager {

    private val context by lazy { SwUtils.application.applicationContext }

    /**
     * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache)
     */
    fun cleanInternalCache() {
        deleteFilesByDirectory(context.cacheDir)
    }

    /**
     * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases)
     */
    fun cleanDatabases() {
        deleteFilesByDirectory(
            File(
                ("/data/data/"
                        + context.packageName) + "/databases"
            )
        )
    }

    /**
     * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) *
     */
    fun cleanSharedPreference() {
        deleteFilesByDirectory(
            File(
                ("/data/data/"
                        + context.packageName) + "/shared_prefs"
            )
        )
    }

    /**
     * 按名字清除本应用数据库
     * @param context
     * @param dbName
     */
    fun cleanDatabaseByName(dbName: String?) {
        context.deleteDatabase(dbName)
    }

    /**
     * 清除/data/data/com.xxx.xxx/files下的内容
     */
    fun cleanFiles() {
        deleteFilesByDirectory(context.filesDir)
    }

    /**
     * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     */
    fun cleanExternalCache() {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        ) {
            deleteFilesByDirectory(context.externalCacheDir)
        }
    }

    /**
     * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
     * @param filePath
     */
    fun cleanCustomCache(filePath: String?) {
        deleteFilesByDirectory(File(filePath))
    }

    /**
     * 清除本应用所有的数据
     * @param filepath
     */
    fun cleanApplicationData(vararg filepath: String?) {
        cleanInternalCache()
        cleanExternalCache()
        cleanDatabases()
        cleanSharedPreference()
        cleanFiles()
        for (fp in filepath) {
            cleanCustomCache(fp)
        }
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
     * @param directory
     */
    private fun deleteFilesByDirectory(directory: File?) {
        if (directory != null && directory.exists() && directory.isDirectory) {
            for (item in directory.listFiles()) {
                item.delete()
            }
        }
    }
}
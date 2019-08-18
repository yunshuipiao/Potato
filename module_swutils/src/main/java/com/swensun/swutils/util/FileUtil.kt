package com.swensun.swutils.util

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception


fun createFile(path: String, name: String): Boolean {
    val folder = File(path)
    if (!folder.exists()) {
        folder.mkdirs()
    }

    val file = File(path, name)
    if (!file.exists()) {
        return try {
            file.createNewFile()
        } catch (e: Exception) {
            false
        }
    } else {
        return true
    }
}

fun getFiles(folder: String, result: ArrayList<File>) {
    val file = File(folder)
    val subFiles = file.listFiles()
    subFiles.forEach {
        if (it.isFile) {
            result.add(0, it)
        } else {
            getFiles(it.path, result)
        }
    }
}

/**
 * 删除给定的文件或者文件夹
 * @param path : 文件或者文件夹路径
 */
fun delFile(path: String) {
    val file = File(path)
    try {
        file.deleteRecursively()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun copyFile(fromFile: String, toFolder: String): Boolean {
    try {
        val from = File(fromFile)
        if (from.isDirectory) {
            throw Exception("fromFile should be file")
        }
        val to = File(toFolder)
        if (!to.exists()) {
            to.mkdirs()
        }
        val toFile = File(toFolder, from.name)
        val inputStream = FileInputStream(from)
        val outputStream = FileOutputStream(toFile)
        val bytes = ByteArray(1024)
        var c: Int = inputStream.read(bytes)
        while (c > 0) {
            outputStream.write(bytes, 0, c)
            c = inputStream.read(bytes)
        }
        inputStream.close()
        outputStream.close()
        return true
    } catch (e: Exception) {
        e.printStackTrace()
        return false
    }
}

fun writeToFile(content: String, filePath: String, append: Boolean = false) {
    val file = File(filePath)
    if (!file.exists()) {
        file.createNewFile()
    }
    if (!file.isFile) {
        throw Exception("should be file")
    }
    var fos: OutputStream? = null
    try {
        fos = FileOutputStream(file, append)
        fos.write(content.toByteArray())
        fos.write("\r\n".toByteArray())
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        fos?.let {
            try {
                fos.close()
            } catch (e: Exception) {
            }
        }
    }
}





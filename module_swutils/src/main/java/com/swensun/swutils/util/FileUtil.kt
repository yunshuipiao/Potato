package com.swensun.swutils.util

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
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

fun getFiles(folder: String): ArrayList<File> {
    val result = arrayListOf<File>()
    val file = File(folder)
    val subFiles = file.listFiles() ?: return result
    subFiles.forEach {
        if (it.isFile) {
            result.add(0, it)
        } else {
            getFiles(it.path)
        }
    }
    return result
}

fun deleteFile(path: String) {
    val files = getFiles(path)
    if (files.size != 0) {
        files.forEach {
            if (it.isFile) {
                it.delete()
            }
        }
    }
}

fun copy(from: String, to: String): Boolean {
    val root = File(from)
    if (!root.exists()) {
        return false
    }
    val curFiles = root.listFiles()
    val targetDir = File(to)
    if (!targetDir.exists())  {
        targetDir.mkdirs()
    }
    curFiles.forEach {
        if (it.isDirectory) {
            copy(it.path + "/", to + it.name + "/")
        } else {
            copyFile(it.path, to + "/" +  it.name)
        }
    }
    return true
}

fun copyFile(from: String, to: String): Boolean {
    try {
        val inputStream = FileInputStream(from)
        val outputStream = FileOutputStream(to)
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
        return false
    }
}

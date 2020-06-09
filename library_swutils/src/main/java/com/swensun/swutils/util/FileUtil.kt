package com.swensun.swutils.util

import java.io.*
import java.text.DecimalFormat
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream
import kotlin.math.log10
import kotlin.math.pow

/**
 * 创建文件
 * @param path：文件路径
 * @param name: 文件名
 */
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

/**
 * 获取文件加下的所有文件
 * @param folder: 文件夹
 * @param result：得到的文件列表
 */
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

/**
 * 复制文件
 * @param fromFile： 需要复制的文件
 * @param toFolder： 目标文件夹
 */
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

/**
 * 压缩文件
 * @param src：需要压缩的文件或者文件夹
 * @param outputFilePath：输出的压缩文件
 */
fun zip(src: String, outputFilePath: String) {
    var zos: ZipOutputStream? = null
    try {
        val outFile = File(outputFilePath)
        val fileOrDirectory = File(src)
        zos = ZipOutputStream(FileOutputStream(outFile))
        if (fileOrDirectory.isFile) {
            zipFileOrDirectory(zos, fileOrDirectory, "")
        } else {
            val files = fileOrDirectory.listFiles()
            files.forEach {
                zipFileOrDirectory(zos, it, "")
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        zos?.let {
            try {
                it.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}

private fun zipFileOrDirectory(zos: ZipOutputStream, fileOrDirectory: File, curPath: String) {
    var fis: FileInputStream? = null
    try {
        if (fileOrDirectory.isFile) {
            val buffer = ByteArray(4096)
            var bytesRead = 0
            fis = FileInputStream(fileOrDirectory)
            val zipEntry = ZipEntry(curPath + fileOrDirectory.name)
            zos.putNextEntry(zipEntry)
            bytesRead = fis.read(buffer)
            while (bytesRead > 0) {
                zos.write(buffer, 0, bytesRead)
                bytesRead = fis.read(buffer)
            }
            zos.closeEntry()
        } else {
            val files = fileOrDirectory.listFiles()
            files.forEach {
                zipFileOrDirectory(zos, it, curPath + fileOrDirectory.name + "/")
            }
        }

    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        fis?.let {
            try {
                it.close()
            } catch (e: Exception) {
            }
        }
    }
}

/**
 * 解压文件
 * @param filePath：需要解压的文件
 * @param outPutFolder：输出的解压文件夹
 */
fun unzip(filePath: String, outPutFolder: String): Boolean {
    val folder = File(outPutFolder)
    if (!folder.exists()) {
        folder.mkdirs()
    }
    var fis: FileInputStream? = null
    var zis: ZipInputStream? = null
    try {
        fis = FileInputStream(filePath)
        zis = ZipInputStream(fis)
        val buffer = ByteArray(1024)
        var zipEntry = zis.nextEntry
        while (zipEntry != null) {
            var name = zipEntry.name
            if (name.contains("../")) {
                fis.close()
                zis.close()
                return false
            }
            val index = name.indexOf("/")
            name = name.substring(if (index < 0) 0 else index)
            val file = File(folder, name)
            if (zipEntry.isDirectory) {
                if (!file.exists()) {
                    file.mkdirs()
                    continue
                }
            }
            val fos = FileOutputStream(file)
            try {
                var count = zis.read(buffer)
                while (count > 0) {
                    fos.write(buffer, 0, count)
                    count = zis.read(buffer)
                }
                zis.closeEntry()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                fos.close()
            }
            zipEntry = zis.nextEntry
        }
    } catch (e: Exception) {
        e.printStackTrace()
        delFile(outPutFolder)
        return false
    } finally {
        zis?.close()
        fis?.close()

    }
    return true
}

/**
 * 格式化文件大小
 */
fun readableFileSize(size: Long): String {
    if (size <= 0) {
        return "0"
    }
    val units = arrayOf("B", "kB", "MB", "GB", "TB")
    val digitGroups = (log10(size.toDouble()) / log10(1024.0)).toInt()
    return DecimalFormat("#,##0.#").format(size / 1024.0.pow(digitGroups.toDouble())) + " " + units[digitGroups]
}






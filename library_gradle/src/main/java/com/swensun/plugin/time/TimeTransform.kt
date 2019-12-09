package com.swensun.plugin.time

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager.CONTENT_CLASS
import com.android.build.gradle.internal.pipeline.TransformManager.SCOPE_FULL_PROJECT
import com.android.utils.FileUtils
import org.apache.commons.codec.digest.DigestUtils

class TimeTransform : Transform() {
    override fun getName(): String {
        return "TimeTransform"
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return CONTENT_CLASS
    }

    override fun isIncremental(): Boolean {
        return false
    }
    
    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return SCOPE_FULL_PROJECT
    }

    override fun transform(transformInvocation: TransformInvocation?) {
        println(" ------- transform begin ---------")
        transformInvocation?.inputs?.forEach { transformInput ->
            transformInput.directoryInputs.forEach { directoryInput ->
                // 注入代码

                
                // 复制修改过的目录到对应的文件夹
                val dest = transformInvocation.outputProvider.getContentLocation(
                    directoryInput.name, directoryInput.contentTypes, directoryInput.scopes,
                    Format.DIRECTORY
                )
                dest.copyRecursively(directoryInput.file, true)

            }
            transformInput.jarInputs.forEach { jarInput ->
                val dest = transformInvocation.outputProvider.getContentLocation(
                    jarInput.name, jarInput.contentTypes, jarInput.scopes,
                    Format.JAR
                )
                dest.copyTo(jarInput.file, true)
            }
        }
        println(" ------- transform end ---------")
    }
}
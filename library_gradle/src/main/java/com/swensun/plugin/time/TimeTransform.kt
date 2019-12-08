package com.swensun.plugin.time

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager.CONTENT_CLASS
import com.android.build.gradle.internal.pipeline.TransformManager.SCOPE_FULL_PROJECT

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
        super.transform(transformInvocation)
        println(" ------- transform begin ---------")
        transformInvocation?.inputs?.forEach {
            it.directoryInputs.forEach {
                println("file: ${it.file}")
            }
        }

        println(" ------- transform end ---------")
    }

}
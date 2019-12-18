package com.swensun.plugin.time

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import org.gradle.api.Project
import java.io.File

object TimeInjectByJavassist {
    val classPool = ClassPool.getDefault()

    fun injectToast(path: String, project: Project) {
        classPool.appendClassPath(path)
        val android = project.extensions.getByType(BaseExtension::class.java)
        classPool.appendClassPath(android.bootClasspath[0].toString())
        classPool.importPackage("android.os.Bundle")


        val dir = File(path)
        if (dir.isDirectory) {
            dir.walkTopDown().forEach {file ->
                 println("file: $file")

                if (file.name == "MainActivity.class") {
                    //获取 Class
                    val ctClass: CtClass = classPool.getCtClass("com.swensun.potato.MainActivity")
                    println("ctClass: $ctClass")

                    //解冻
                    if (ctClass.isFrozen){
                        ctClass.defrost()
                    }

                    val ctMethod: CtMethod = ctClass.getDeclaredMethod("test")
                    println("ctMethod: $ctMethod")

                    val toastStr = """ android.widget.Toast.makeText(this,"插入的Toast代码~",android.widget.Toast.LENGTH_SHORT).show();  
                                      """
                    ctMethod.insertBefore(toastStr)
                    ctClass.writeFile(path)
                    ctClass.detach()
                }
            }
        }
    }
}
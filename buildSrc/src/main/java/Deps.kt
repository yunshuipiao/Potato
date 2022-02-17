package com.swensun.buildsrc

object Versions {
    @JvmStatic
    val gradleVersion = "4.0.2"

    @JvmStatic
    val kotlinVersion = "1.5.0"

    @JvmStatic
    val compileSdkVersion = 28

    @JvmStatic
    val minSdkVersion = 21

    @JvmStatic
    val targetSdkVersion = 28

    @JvmStatic
    val buildToolsVersion = "28.0.3"
}

object ClassPathDep {
    @JvmStatic
    val buildGradleVersion = "com.android.tools.build:gradle:${Versions.gradleVersion}"

    @JvmStatic
    val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
}

object KotlinDep {
    @JvmStatic
    val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.0"

    @JvmStatic
    val kotlinx_coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0"

    @JvmStatic
    val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:1.5.0"

    @JvmStatic
    val kotlinx_coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0"
}

object AndroidxDep {
    @JvmStatic
    val swiperefresh = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

    @JvmStatic
    val appcompat = "androidx.appcompat:appcompat:1.4.0-alpha02"

    @JvmStatic
    val coreKtx = "androidx.core:core-ktx:1.6.0-rc01"

    @JvmStatic
    val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.4"

    @JvmStatic
    val flex = "com.google.android:flexbox:2.0.1"

    @JvmStatic
    val workRuntime = "androidx.work:work-runtime:2.5.0"

    @JvmStatic
    val room_runtime = "androidx.room:room-runtime:2.3.0"

    @JvmStatic
    val room_ktx = "androidx.room:room-ktx:2.3.0"

    @JvmStatic
    val fragment_ktx = "androidx.fragment:fragment-ktx:1.3.5"

    @JvmStatic
    val lifecycle_livedata_ktx = "androidx.lifecycle:lifecycle-livedata-ktx:2.3.1"

    @JvmStatic
    val lifecycle_viewmodel_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1"

    @JvmStatic
    val lifecycle_runtime_ktx = "androidx.lifecycle:lifecycle-runtime-ktx:2.4.0-alpha02"

    @JvmStatic
    val lifecycle_extensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"

    @JvmStatic
    val recyclerview = "androidx.recyclerview:recyclerview:1.2.1"

    @JvmStatic
    val material = "com.google.android.material:material:1.4.0"

}

object ThirdPartyDep {
    @JvmStatic
    val glance = "com.glance.guolindev:glance:1.0.0"

    @JvmStatic
    val exoplayer = "com.google.android.exoplayer:exoplayer:2.14.1"

    @JvmStatic
    val gson = "com.google.code.gson:gson:2.8.6"

    @JvmStatic
    val conscrypt_android = "org.conscrypt:conscrypt-android:2.1.0"

    @JvmStatic
    val coil = "io.coil-kt:coil:0.6.1"

    @JvmStatic
    val multiType = "com.drakeet.multitype:multitype:4.2.0"

    @JvmStatic
    val stetho = "com.facebook.stetho:stetho:1.5.1"

    @JvmStatic
    val okdownload_okhttp = "com.liulishuo.okdownload:okhttp:1.0.6"

    @JvmStatic
    val okdownload_sqlite = "com.liulishuo.okdownload:sqlite:1.0.6"

    @JvmStatic
    val okdownload = "com.liulishuo.okdownload:okdownload:1.0.6"

    @JvmStatic
    val permission = "com.yanzhenjie:permission:2.0.3"

    @JvmStatic
    val utilcodex = "com.blankj:utilcodex:1.30.6"

    @JvmStatic
    val leakcanary = "com.squareup.leakcanary:leakcanary-android:2.7"

    @JvmStatic
    val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"

    @JvmStatic
    val converter_gson = "com.squareup.retrofit2:converter-gson:2.9.0"

    @JvmStatic
    val glide = "com.github.bumptech.glide:glide:4.12.0"
}

object KaptDep {
    @JvmStatic
    val roomCompiler = "androidx.room:room-compiler:2.3.0"

    @JvmStatic
    val glideCompiler = "com.github.bumptech.glide:compiler:4.12.0"
}

object TestDep {
    @JvmStatic
    val junit = "junit:junit:4.12"

    @JvmStatic
    val runner = "androidx.test:runner:1.1.0-alpha4"

    @JvmStatic
    var expresso = "androidx.test.espresso:espresso-core:3.1.0-alpha4"
}
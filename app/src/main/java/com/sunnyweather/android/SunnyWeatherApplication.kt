package com.sunnyweather.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * 全局提供获取context
 * 也可以子啊object中定义全局变量，方便之后的获取
 */
class SunnyWeatherApplication: Application() {
    companion object {
        // 该注解用于忽略警告： Do not place Android context classes in static fields; this is a memory leak
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        const val TOKEN = "申请到的TOKEN值"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}
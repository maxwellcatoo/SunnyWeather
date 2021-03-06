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

        const val isGApi = true
        // 彩云天气api的key值
        const val TOKEN = "TOKEN值"
        // 高德地图 天气查询api申请到的key
        const val G_TOKEN = "905e6313ffef596c29552a1e04fb608c"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}
package com.sunnyweather.android.logic.model

data class Weather(val realtime: RealtimeResponse.Realtime, val daily: DailyResponse.Daily)

// 高德天气api对应数据类
data class GWeather(val realtime: GRealtimeResponse, val daily: GDailyResponse)
package com.sunnyweather.android.logic.newwork

import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.DailyResponse
import com.sunnyweather.android.logic.model.GDailyResponse
import com.sunnyweather.android.logic.model.GRealtimeResponse
import com.sunnyweather.android.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {
    // 用于获取实时的天气信息
        // @Path接口用于向接口中动态传递经纬度的坐标
//    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    @GET("v3/weather/weatherInfo?key=${SunnyWeatherApplication.G_TOKEN}")
    fun getRealtimeWeather(@Query("city") adCode: String): Call<GRealtimeResponse>

    // 用来获取未来的天气信息
    @GET("v3/weather/weatherInfo?extensions=all&key=${SunnyWeatherApplication.G_TOKEN}")
    fun getDailyWeather(@Query("city") adCode: String): Call<GDailyResponse>
}
package com.sunnyweather.android.logic.newwork

import com.sunnyweather.android.SunnyWeatherApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object SunnyWeatherNetwork {
    private val placeService = ServiceCreator.create<PlaceService>()
    private val weatherService = ServiceCreator.create<WeatherService>()

    // 将searchPlaces方法声明成挂起函数
//    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await() // 彩云api
    suspend fun searchPlaces(query: String) = placeService.searchGPlaces(query).await() // 高德api
    suspend fun getDailyWeather(adCode: String) = weatherService.getDailyWeather(adCode).await()
    suspend fun getRealtimeWeather(adCode: String) = weatherService.getRealtimeWeather(adCode).await()

    // 下面这几行代码完全看不懂啊兄弟！！！（await方法被上面调用了）
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object: Callback<T> {

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if(body != null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response body is null")
                    )
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}
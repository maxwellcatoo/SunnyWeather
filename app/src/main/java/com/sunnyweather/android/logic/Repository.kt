package com.sunnyweather.android.logic

import androidx.lifecycle.liveData
import com.sunnyweather.android.logic.model.Place
import com.sunnyweather.android.logic.model.Weather
import com.sunnyweather.android.logic.newwork.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

/**
 * 仓库层的统一封装入口
 */
object Repository {
    // 指定参数为Dispatchers.IO，这样代码块中的所有代码就都运行在子线程中了（安卓不允许在主线程中请求网络数据）
/*    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        println("hello $query world")
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query) // 取到请求到的数据
            println("hello   ${placeResponse}")
            if(placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            println("hello 发生了一些错误$e") // 手机模拟器时间不正确导致try那里出现一些问题
            Result.failure<List<Place>>(e)
        }
        // emit方法类似于调用LiveData的setValue方法来通知数据变化（因为这个位置无法取得liveData对象，这个是提供liveData方法的lifecycle-livedata-ktx库提供的）
        emit(result)
    }

    fun refreshWeather(lng: String, lat: String) = liveData(Dispatchers.IO) {
        val result = try {
            // async函数只能在协程作用域中才能调用，所以这里使用了coroutineScope函数创建了一个协程作用域
                // 使用async的目的是，并发执行两个请求，提高程序的运行效率(并发执行的使用方式！！！)
            coroutineScope {
                val deferredRealtime = async {
                    SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
                }

                val deferredDaily = async {
                    SunnyWeatherNetwork.getDailyWeather(lng, lat)
                }
                val realtimeResponse = deferredRealtime.await()
                val dailyResponse = deferredDaily.await()
                if(realtimeResponse.status == "ok" && dailyResponse.status == "ok"){
                    val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                    Result.success(weather)
                }else{
                    Result.failure(
                        RuntimeException("realtime response status is ${realtimeResponse.status}" + "/n" + "daily response status is ${dailyResponse.status}")
                    )
                }
            }
        }catch(e: Exception){
            Result.failure<Weather>(e)
        }
        emit(result)
    }*/

    // 使用fire方法处理
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        println("hello $query world")
        val placeResponse = SunnyWeatherNetwork.searchPlaces(query) // 取到请求到的数据
        println("hello   ${placeResponse}")
        if(placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
            // async函数只能在协程作用域中才能调用，所以这里使用了coroutineScope函数创建了一个协程作用域
            // 使用async的目的是，并发执行两个请求，提高程序的运行效率(并发执行的使用方式！！！)
            coroutineScope {
                val deferredRealtime = async {
                    SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
                }

                val deferredDaily = async {
                    SunnyWeatherNetwork.getDailyWeather(lng, lat)
                }
                val realtimeResponse = deferredRealtime.await()
                val dailyResponse = deferredDaily.await()
                if(realtimeResponse.status == "ok" && dailyResponse.status == "ok"){
                    val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                    Result.success(weather)
                }else{
                    Result.failure(
                        RuntimeException("realtime response status is ${realtimeResponse.status}" + "/n" + "daily response status is ${dailyResponse.status}")
                    )
                }
            }
    }
    // 定义一个fire方法，统一处理try-catch处理，供上面两个方法使用
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) = liveData<Result<T>>(context){
        val result = try {
            block()
        }catch(e: Exception){
            Result.failure<T>(e)
        }
        emit(result)
    }
}
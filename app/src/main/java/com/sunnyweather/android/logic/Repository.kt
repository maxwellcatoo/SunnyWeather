package com.sunnyweather.android.logic

import androidx.lifecycle.liveData
import com.sunnyweather.android.logic.model.Place
import com.sunnyweather.android.logic.newwork.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

/**
 * 仓库层的统一封装入口
 */
object Repository {
    // 指定参数为Dispatchers.IO，这样代码块中的所有代码就都运行在子线程中了（安卓不允许在主线程中请求网络数据）
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query) // 取到请求到的数据
            if(placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure((RuntimeException("response status is ${placeResponse.status}")))
            }
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }
        // emit方法类似于调用LiveData的setValue方法来通知数据变化（因为这个位置无法取得liveData对象，这个是提供liveData方法的lifecycle-livedata-ktx库提供的）
        emit(result)
    }
}
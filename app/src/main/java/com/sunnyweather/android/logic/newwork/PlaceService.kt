package com.sunnyweather.android.logic.newwork

import android.util.Log
import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaceService {
    // 在searchPlaces方法上面声明一个GET注解，这样当调用search-Places方法的时候，Retrofit就会自动发起一条GET请求，去访问@GET注解中配置的地址。其中搜索城市数据的API只有query这个参数是需要动态指定的，我们使用@Query注解的方式来进行实现，另外两个参数是不会变得，因此固定写在@GET注解中即可。
    // 另外，searchPlaces()方法的返回值被声明成了Call<PlaceResponse>，这样Retrofit就会将服务器放回的JSON数据自动解析成PlaceResponse对象了。
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}

object ServiceCreator {
    private const val BASE_URL = "https://api.caiyunapp.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun<T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}


data class Data(val id: String, val name: String)
interface ExampleService {
    @GET("{page}get_data.json")
    fun getData(@Path("page") page: Int): Call<Data>
}


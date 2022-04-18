package com.sunnyweather.android.logic.newwork

import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.GPlaceResponse
import com.sunnyweather.android.logic.model.PlaceResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


/* Retrofit构建器的最佳写法 */
interface PlaceService {
    // 在searchPlaces方法上面声明一个GET注解，这样当调用searchPlaces方法的时候，Retrofit就会自动发起一条GET请求，去访问@GET注解中配置的地址。其中搜索城市数据的API只有query这个参数是需要动态指定的，我们使用@Query注解的方式来进行实现，另外两个参数是不会变得，因此固定写在@GET注解中即可。
    // 另外，searchPlaces()方法的返回值被声明成了Call<PlaceResponse>，这样Retrofit就会将服务器返回的JSON数据自动解析成PlaceResponse对象了。
//    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    @GET("v2/place?lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>

    /*高德地图api接口*/
    @GET("/v3/place/text?extensions=all&key=${SunnyWeatherApplication.G_TOKEN}")
    fun searchGPlaces(
        @Query("keywords") keywords: String,
//        @Query("city") city: String?,
//        @Query("types") types: String?,
//        @Query("citylimit") citylimit: String?,
//        @Query("output") output: String?
    ): Call<GPlaceResponse>


}
// 单例模式
object ServiceCreator {
    private val BASE_URL by lazy {
        if(SunnyWeatherApplication.isGApi) {
            "https://restapi.amap.com/"
        }else{
            "https://api.caiyunapp.com/"
        }
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    // 每次调用create方法，拿到的都是通过这个retrofit的create方法对象，获取方法如下：
        // val appService = ServiceCreator.create(AppService::class.java)
    fun<T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
    // 相较于上一种方法的进一步改进  泛型实化功能，使用方法：
        // val appService = ServiceCreator.create<AppService>()
    inline fun <reified T> create(): T = create(T::class.java)
}

/* 处理复杂的接口地址类型 */
/*data class Data1(val id: String, val name: String)
interface ExampleService {
    @Headers("User-Agent: okhttp", "Cache-Control: max-age=0")
    @GET("get_data.json")
    fun getData(@Query("u") user: String, @Query("t") token: String): Call<Data1>

    @GET("get_data2.json")
    fun getData2(@Header("User-Agent") userAgent: String,@Header("Cache-Control") cacheControl: String)

    @DELETE("data/{id}")
    fun deleteData(@Path("id") id: Int): Call<ResponseBody>

    @POST("data/create")
    fun createData(@Body data: Data1): Call<ResponseBody>
}*/
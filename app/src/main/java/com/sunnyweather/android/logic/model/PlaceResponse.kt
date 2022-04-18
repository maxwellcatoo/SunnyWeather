package com.sunnyweather.android.logic.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(val status: String, val places: List<Place>)

data class Place(val name: String, val location: Location,
@SerializedName("formatted_address") val address: String)

data class Location(val lng: String, val lat: String)

// 高德天气api对应数据类
data class GPlaceResponse(val status: String, @SerializedName("pois") val places: List<GPlace>, val count: Int)
data class GPlace(val name: String, val adcode: String, val location: String, val pname: String, val cityname: String, val adname: String, val address: Any) // 这个address的数据类型原来是String，我也不想写成Any，但是，上海的有个地方的address是一个空数组。。。这就很让人无语。。。

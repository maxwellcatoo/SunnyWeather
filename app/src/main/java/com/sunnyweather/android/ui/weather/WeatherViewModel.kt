package com.sunnyweather.android.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sunnyweather.android.logic.Repository
import com.sunnyweather.android.logic.model.Location

class WeatherViewModel: ViewModel(){
    private val locationLiveData: MutableLiveData<String> = MutableLiveData<String>()

    var adCode = ""

    var placeName = ""

    val weatherViewModel = Transformations.switchMap(locationLiveData) { adCode ->
        Repository.refreshWeather(adCode)
    }

    fun refreshWeather(adCode: String) {
        locationLiveData.value = adCode
    }
}
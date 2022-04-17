package com.sunnyweather.android.ui.place

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sunnyweather.android.logic.Repository
import com.sunnyweather.android.logic.model.GPlace
import com.sunnyweather.android.logic.model.Place

// ViewModel层（相当于逻辑层与UI层的一个桥梁）
class PlaceViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<GPlace>()

    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        println("hello $query")
        Log.d("searchPlace",query)
        Repository.searchPlaces(query)
    }

    fun searchPlace(query: String) {
        searchLiveData.value = query
    }
}
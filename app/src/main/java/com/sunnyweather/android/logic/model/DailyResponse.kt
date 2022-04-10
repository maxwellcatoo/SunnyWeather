package com.sunnyweather.android.logic.model

import android.provider.ContactsContract
import com.google.gson.annotations.SerializedName

data class DailyResponse(val status: String, val result: Result) {
    data class Result(val daily: Daily)

    data class Daily(val temperature: List<Temperature>, val skycon: List<Skycon>, @SerializedName("life_index") val lifeIndex: LifeIndex)

    data class Temperature(val max: Float, val min: Float)

    data class Skycon(val value: String, val data: ContactsContract.Contacts.Data)

    data class LifeIndex(val coldRik: List<LifeDescription>, val carWashing: List<LifeDescription>, val ustraviolet: List<LifeDescription>, val dressing: List<LifeDescription>)

    data class LifeDescription(val desc: String)
}
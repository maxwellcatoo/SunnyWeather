package com.sunnyweather.android.ui.weather

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sunnyweather.android.R
import com.sunnyweather.android.logic.model.GWeather
import com.sunnyweather.android.logic.model.Weather
import com.sunnyweather.android.logic.model.getSky
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.forecast.*
import kotlinx.android.synthetic.main.life_index.*
import kotlinx.android.synthetic.main.now.*
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProvider(this).get(WeatherViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val decorView = window.decorView
        // 改变系统UI的显示
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT // 将状态栏设置成透明色
        setContentView(R.layout.activity_weather)

        if (viewModel.adCode.isEmpty()) {
            viewModel.adCode = intent.getStringExtra("ad_code") ?: ""
        }
//        if (viewModel.locationLat.isEmpty()) {
//            viewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
//        }
        if (viewModel.placeName.isNotEmpty()) {
            viewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }
        viewModel.weatherViewModel.observe(this, Observer{ result ->
            val weather = result.getOrNull()
            if (weather != null) {
                // 拿到数据，就显示拿到的天气信息
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace() // todo 没看明白这一行干啥的
            }
        })
        // 下面这行代码会触发上面那个代码块运行，从而更新显示天气信息或弹窗报错
        viewModel.refreshWeather(viewModel.adCode)
    }

    /**
     * 在控件中显示天气信息
     */
    private fun showWeatherInfo(weather: GWeather) {
        placeName.text = viewModel.placeName
        val realtime = weather.realtime.lives[0]
        val daily = weather.daily
        // 填充now.xml布局中的数据
        val currentTempText = "${realtime.temperature} °C"
        currentTemp.text = currentTempText
        currentSky.text = getSky(realtime.weather).info
        val currentPM25Text = "空气指数 ${"未知"}"
        currentAQI.text = currentPM25Text
        nowLayout.setBackgroundColor(getSky(realtime.weather).bg)
        // 填充forecast.xml布局中的数据
        forecastLayout.removeAllViews()
        val days = daily.forcecasts.size
        for(i in 0 until days) {
            val skycon = daily.forcecasts[i].dayweather
            val dayTemperature = daily.forcecasts[i].daytemp
            val nightTemperature = daily.forcecasts[i].nighttemp
            val view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false)
            val dateInfo = view.findViewById(R.id.dateInfo) as TextView
            val skyIcon = view.findViewById(R.id.skyIcon) as ImageView
            val skyInfo = view.findViewById(R.id.skyInfo) as TextView
            val temperatureInfo = view.findViewById(R.id.temperatureInfo) as TextView
//            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//            dateInfo.text = simpleDateFormat.format(skycon.date)
            dateInfo.text = daily.forcecasts[i].date
            val sky = getSky("未知")
            skyIcon.setImageResource(sky.icon)
            val tempText = "$nightTemperature ~ $dayTemperature °C"
            temperatureInfo.text = tempText
            forecastLayout.addView(view)
        }
        // 填充life_index.xml布局中的数据
//        val lifeIndex = daily.lifeIndex
//        coldRiskText.text = lifeIndex.coldRisk[0].desc
//        dressingText.text = lifeIndex.ultraviolet[0].desc
//        ultravioletText.text = lifeIndex.ultraviolet[0].desc
//        carWashingText.text = lifeIndex.carWashing[0].desc
        coldRiskText.text = "0"
        dressingText.text = "0"
        ultravioletText.text = "0"
        carWashingText.text = "0"
        weatherLayout.visibility = View.VISIBLE
    }
}
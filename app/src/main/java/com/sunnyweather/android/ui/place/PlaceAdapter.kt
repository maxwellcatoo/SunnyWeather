package com.sunnyweather.android.ui.place

import android.content.Intent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sunnyweather.android.R
import com.sunnyweather.android.logic.model.GPlace
import com.sunnyweather.android.logic.model.Place
import com.sunnyweather.android.ui.weather.WeatherActivity

class PlaceAdapter(private val fragment: Fragment, private val placeList: List<GPlace>):
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeName: TextView = view.findViewById(R.id.placeName)
        val placeAddress: TextView = view.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item,parent,false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
//            val position = holder.adapterPosition // 书上是这么写的，但是这种写法已经快被废弃了，推荐下面这种写法
            val position = holder.bindingAdapterPosition
            val place = placeList[position]
            val intent = Intent(parent.context, WeatherActivity::class.java).apply {
//                putExtra("location_lng", place.location.split(",")[0])
//                putExtra("location_lat", place.location.split(",")[1])
                putExtra("place_name", place.run{pname + cityname + adname + address})
                putExtra("ad_code", place.adcode)
            }
            fragment.startActivity(intent)
//            fragment.activity?.finish() // 这里不要销毁，不然就返回不到查询页面了
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder,position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.run{pname + cityname + adname + address}
    }

    override fun getItemCount() = placeList.size
}
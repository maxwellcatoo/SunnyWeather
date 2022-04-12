package com.sunnyweather.android.ui.place

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunnyweather.android.R

import kotlinx.android.synthetic.main.fragment_place.*
import org.w3c.dom.Text

class PlaceFragment: Fragment() {
    private val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }

    private lateinit var adapter: PlaceAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_place, container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        adapter = PlaceAdapter(this, viewModel.placeList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
/*        searchPlaceEdit.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("DASD","1")
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("DASD", "2")
            }
//            override fun afterTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                Log.d("DASD", "3")
//            }
        })
        searchPlaceEdit.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?,p1:Int,p2:Int,p3:Int){}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int){}
            override fun afterTextChanged(p0: Editable?) {}
        })*/
        // androidx.core:core-ktx:1.0.0   1.0.0版本没有下面这行的addTextChangedListener这样的用法。。。
        // 必须要把版本升到1.1.0版本才行。。。晕，卡了好久，无语了，书上也不写详细点。。。
        searchPlaceEdit.addTextChangedListener{ editable ->
            println("hello 监听到输入框数据变化，值为$editable")
            val content = editable.toString()
            if(content.isNotEmpty()) {
                viewModel.searchPlace(content)
            } else {
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }
        // viewLifecycleOwner那个位置写成this报错，不知道为啥。。。书上写的this，不记得有报错啊。。。难道是哪一个库的版本的原因？（如果是在Activity中，继承AppCompatActivity类的话，可以用this）
        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer{ result ->
            val places = result.getOrNull()
            if (places != null) {
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

    }
}

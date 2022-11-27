package com.yo.weatherapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : Fragment()  {

    private val viewModel: ViewModel by activityViewModels()
    private lateinit var weatherItemList: ArrayList<WeatherItems>
    private lateinit var adapter: WeatherAdapter

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
//        weatherItemList = viewModel.mWeatherItemList
//        Log.i("BottomSheet", "$weatherItemList")
//        val data = ArrayList<ItemsViewModel>()
//        for (i in 1..20) {
//            data.add(ItemsViewModel(R.drawable.a01d, "Item $i"))
//        }
//
//        val recyclerView: RecyclerView = view.findViewById(R.id.recycleView)
//        recyclerView.setHasFixedSize(true)
//        recyclerView.layoutManager = LinearLayoutManager(activity)
//        val adapter =WeatherAdapter(data)
//        recyclerView.adapter =adapter
//
//        return view
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = ArrayList<ItemsViewModel>()
        for (i in 1..20) {
            data.add(ItemsViewModel(R.drawable.a01d, "Item $i"))
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = WeatherAdapter(data)
        recyclerView.adapter =adapter

    }


}
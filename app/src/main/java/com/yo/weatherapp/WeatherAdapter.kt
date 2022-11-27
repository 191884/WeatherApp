package com.yo.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class WeatherAdapter(private val mList: ArrayList<ItemsViewModel>): RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.days_view, parent,false)
//        return ViewHolder(view)
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.days_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]

//        val date = item.getmDate()
//        val tempAvr = item.getmTempAvr()
//        val weather = item.getmWeather()

        holder.tempOnBottomSheet.text = item.text
//        holder.timeOnBottomSheet.text = date

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) :RecyclerView.ViewHolder(ItemView){
        var timeOnBottomSheet: TextView = itemView.findViewById(R.id.timeOnBottomSheet)
        var tempOnBottomSheet: TextView = itemView.findViewById(R.id.tempOnBottomSheet)
//        var iconOnBottomSheet: ImageView  = binding.iconOnBottomSheet
    }
}
package com.snappy.weathermate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class WeatherAdapter(private val mList: ArrayList<WeatherItems>): RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.days_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]

        holder.timeOnBottomSheet.text = item.mDate
        holder.tempOnBottomSheet.text = item.mTempAvr.toString()
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) :RecyclerView.ViewHolder(ItemView){
        var timeOnBottomSheet: TextView = itemView.findViewById(R.id.timeOnBottomSheet)
        var tempOnBottomSheet: TextView = itemView.findViewById(R.id.tempOnBottomSheet)
    }
}
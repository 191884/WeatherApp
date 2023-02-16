package com.snappy.weathermate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
        holder.tempOnBottomSheet.text = item.mTempAvr

        when (item.mWeather) {
            "01d" -> holder.iconOnBottomSheet.setImageResource(R.drawable.a01d)
            "02d" -> holder.iconOnBottomSheet.setImageResource(R.drawable.a02d)
            "03d" -> holder.iconOnBottomSheet.setImageResource(R.drawable.a03d)
            "04d" -> holder.iconOnBottomSheet.setImageResource(R.drawable.a04d)
            "09d" -> holder.iconOnBottomSheet.setImageResource(R.drawable.a09d)
            "10d" -> holder.iconOnBottomSheet.setImageResource(R.drawable.a10d)
            "11d" -> holder.iconOnBottomSheet.setImageResource(R.drawable.a11d)
            "13d" -> holder.iconOnBottomSheet.setImageResource(R.drawable.a13d)
            "50d" -> holder.iconOnBottomSheet.setImageResource(R.drawable.a50d)
            "01n" -> holder.iconOnBottomSheet.setImageResource(R.drawable.a01d)
            "02n" -> holder.iconOnBottomSheet.setImageResource(R.drawable.a02d)
            "03n" -> holder.iconOnBottomSheet.setImageResource(R.drawable.a03d)
            "04n" -> holder.iconOnBottomSheet.setImageResource(R.drawable.a04d)
            "09n" -> holder.iconOnBottomSheet.setImageResource(R.drawable.a09d)
            "10n" -> holder.iconOnBottomSheet.setImageResource(R.drawable.a10d)
            "11n" -> holder.iconOnBottomSheet.setImageResource(R.drawable.a11d)
            "13n" -> holder.iconOnBottomSheet.setImageResource(R.drawable.a13d)
            "50n" -> holder.iconOnBottomSheet.setImageResource(R.drawable.a50d)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) :RecyclerView.ViewHolder(ItemView){
        var timeOnBottomSheet: TextView = itemView.findViewById(R.id.timeOnBottomSheet)
        var tempOnBottomSheet: TextView = itemView.findViewById(R.id.tempOnBottomSheet)
        var iconOnBottomSheet: ImageView = itemView.findViewById(R.id.iconOnBottomSheet)
    }
}
package com.yo.weatherapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.yo.weatherapp.databinding.FragmentMainBinding
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class MainFragment : Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding
    private lateinit var weatherItemList: ArrayList<WeatherItems>
    private val viewModel: ViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
//        bottomSheetBehavior = BottomSheetBehavior.from( binding!!.root.findViewById(R.id.persistent_bottom_sheet))
        weatherItemList = ArrayList()

        val bundle = arguments
        val lat = bundle?.get("lat").toString()
        val long = bundle?.get("long").toString()

        getJsonData(lat, long)
        viewModel.mWeatherItemList = weatherItemList
        return binding!!.root
    }

    private fun getJsonData(lat: String, long: String)
    {
        Log.e("MainFragment", "$lat $long")

        val API_KEY="60c6fbeb4b93ac653c492ba806fc346d"
        val queue = Volley.newRequestQueue(activity)
        val url ="https://api.openweathermap.org/data/2.5/forecast/daily?units=metric&lat=${lat}&lon=${long}&appid=${API_KEY}"
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            { response ->
                setValues(response)
            },
            { Toast.makeText(activity,"ERROR", Toast.LENGTH_LONG).show() })


        queue.add(jsonRequest)
    }

    private fun setValues(response: JSONObject) {
        binding!!.locationSign.setImageResource(R.drawable.ic_baseline_location)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding!!.timeAndDay.text = LocalDate.now().dayOfWeek.name
        }
        binding!!.city.text = response.getJSONObject("city").getString("name")

        var list = response.getJSONArray("list")
        var jsonArray = list.getJSONObject(0)
        var tempr = jsonArray.getJSONObject("temp").getString("day")
//        tempr = ((((tempr).toFloat() - 273.15)).toInt()).toString()
        binding!!.temp.text = "${tempr}°"
        binding!!.weather.text = jsonArray.getJSONArray("weather").getJSONObject(0).getString("main")
        binding!!.weatherDescription.text = jsonArray.getJSONArray("weather").getJSONObject(0).getString("description")
        var iconID = jsonArray.getJSONArray("weather").getJSONObject(0).getString("icon")
        Log.e("ICon", "$iconID")

        when (jsonArray.getJSONArray("weather").getJSONObject(0).getString("icon")) {
            "01d" -> binding!!.tempImg.setImageResource(R.drawable.a01d)
            "02d" -> binding!!.tempImg.setImageResource(R.drawable.a02d)
            "03d" -> binding!!.tempImg.setImageResource(R.drawable.a03d)
            "04d" -> binding!!.tempImg.setImageResource(R.drawable.a04d)
            "09d" -> binding!!.tempImg.setImageResource(R.drawable.a09d)
            "10d" -> binding!!.tempImg.setImageResource(R.drawable.a10d)
            "11d" -> binding!!.tempImg.setImageResource(R.drawable.a11d)
            "13d" -> binding!!.tempImg.setImageResource(R.drawable.a13d)
            "50d" -> binding!!.tempImg.setImageResource(R.drawable.a50d)
            "01n" -> binding!!.tempImg.setImageResource(R.drawable.a01d)
            "02n" -> binding!!.tempImg.setImageResource(R.drawable.a02d)
            "03n" -> binding!!.tempImg.setImageResource(R.drawable.a03d)
            "04n" -> binding!!.tempImg.setImageResource(R.drawable.a04d)
            "09n" -> binding!!.tempImg.setImageResource(R.drawable.a09d)
            "10n" -> binding!!.tempImg.setImageResource(R.drawable.a10d)
            "11n" -> binding!!.tempImg.setImageResource(R.drawable.a11d)
            "13n" -> binding!!.tempImg.setImageResource(R.drawable.a13d)
            "50n" -> binding!!.tempImg.setImageResource(R.drawable.a50d)
        }
        for (i in 1 until list.length()) {
            val tempList = list.getJSONObject(i)
            val tempInList: JSONObject = tempList.getJSONObject("temp")
            val weatherArray = tempList.getJSONArray("weather")
            val dataWeather = weatherArray.getJSONObject(0)
            val date = tempList.getLong("dt")
            val transformedDate: String =
                SimpleDateFormat("dd MMM yyyy  hh:mm").format(Date(date * 1000))
            val tempAvr: Double = (tempInList.getDouble("min") + tempInList.getDouble("max")) / 2
            val weather = dataWeather.getString("description")
            weatherItemList.add(WeatherItems(transformedDate, tempAvr, weather))
        }
        Log.i("Trying", "$weatherItemList")

    }

}
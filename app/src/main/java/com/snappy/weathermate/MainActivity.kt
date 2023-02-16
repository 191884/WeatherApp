package com.snappy.weathermate

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.snappy.weathermate.databinding.ActivityMainBinding
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Getting Latitude & Longitude From Location through SplashActivity
//        val lat = intent.getStringExtra("lat")
//        val long = intent.getStringExtra("long")
        val lat =28.7975 .toString()
        val long = 76.1322 .toString()

        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        getJsonData(lat!!, long!!)

    }

    private fun getJsonData(lat: String, long: String)
    {
        Log.e("MainFragment", "$lat $long")

        val API_KEY="60c6fbeb4b93ac653c492ba806fc346d"
        val queue = Volley.newRequestQueue(this)
        val url ="https://api.openweathermap.org/data/2.5/forecast/daily?units=metric&lat=${lat}&lon=${long}&appid=${API_KEY}"
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            { response ->
                setValues(response)
            },
            { Toast.makeText(this,"ERROR", Toast.LENGTH_LONG).show() })
        queue.add(jsonRequest)
    }

    private fun setValues(response: JSONObject){
        binding.locationSign.setImageResource(R.drawable.ic_baseline_location)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.timeAndDay.text = LocalDate.now().dayOfWeek.name
        }

        binding.city.text = response.getJSONObject("city").getString("name")

        val list = response.getJSONArray("list")
        val jsonArray = list.getJSONObject(0)
        val temp = jsonArray.getJSONObject("temp").getString("day")
//        tempr = ((((tempr).toFloat() - 273.15)).toInt()).toString()
        binding.temp.text = "${temp}°"
        binding.weather.text = jsonArray.getJSONArray("weather").getJSONObject(0).getString("main")
        binding.weatherDescription.text = jsonArray.getJSONArray("weather").getJSONObject(0).getString("description")
        val iconID = jsonArray.getJSONArray("weather").getJSONObject(0).getString("icon")
        Log.e("ICon", "$iconID")

        when (jsonArray.getJSONArray("weather").getJSONObject(0).getString("icon")) {
            "01d" -> binding.tempImg.setImageResource(R.drawable.a01d)
            "02d" -> binding.tempImg.setImageResource(R.drawable.a02d)
            "03d" -> binding.tempImg.setImageResource(R.drawable.a03d)
            "04d" -> binding.tempImg.setImageResource(R.drawable.a04d)
            "09d" -> binding.tempImg.setImageResource(R.drawable.a09d)
            "10d" -> binding.tempImg.setImageResource(R.drawable.a10d)
            "11d" -> binding.tempImg.setImageResource(R.drawable.a11d)
            "13d" -> binding.tempImg.setImageResource(R.drawable.a13d)
            "50d" -> binding.tempImg.setImageResource(R.drawable.a50d)
            "01n" -> binding.tempImg.setImageResource(R.drawable.a01d)
            "02n" -> binding.tempImg.setImageResource(R.drawable.a02d)
            "03n" -> binding.tempImg.setImageResource(R.drawable.a03d)
            "04n" -> binding.tempImg.setImageResource(R.drawable.a04d)
            "09n" -> binding.tempImg.setImageResource(R.drawable.a09d)
            "10n" -> binding.tempImg.setImageResource(R.drawable.a10d)
            "11n" -> binding.tempImg.setImageResource(R.drawable.a11d)
            "13n" -> binding.tempImg.setImageResource(R.drawable.a13d)
            "50n" -> binding.tempImg.setImageResource(R.drawable.a50d)
        }
        for (i in 1 until 7) {
            val tempList = list.getJSONObject(i)
            val tempInList: JSONObject = tempList.getJSONObject("temp")
            val weatherArray = tempList.getJSONArray("weather")
            val dataWeather = weatherArray.getJSONObject(0)
            val date = tempList.getLong("dt")
            val transformedDate: String = SimpleDateFormat("EEEE").format(Date(date * 1000))
//                android.text.format.DateFormat.format("E", date).toString()

            val tempAvr: String = ((tempInList.getInt("min")).toString()+ "°C" + " / " + (tempInList.getInt("max")) / 2) + "°C"
            val weather = dataWeather.getString("description")
            viewModel.mWeatherItemList.add(WeatherItems(transformedDate, tempAvr, weather))
//            weatherItemList.add(WeatherItems(transformedDate, tempAvr, weather))
            Log.i("Trying recyclerView", "${viewModel.mWeatherItemList.size}")
        }
        val recyclerView: RecyclerView = findViewById(R.id.recycleView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter: WeatherAdapter =WeatherAdapter(viewModel.mWeatherItemList)
        recyclerView.adapter =adapter
    }
}
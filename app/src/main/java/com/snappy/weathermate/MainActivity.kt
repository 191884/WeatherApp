package com.snappy.weathermate

import android.os.Build
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.snappy.weathermate.databinding.ActivityMainBinding
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val animationZoomOut = AnimationUtils.loadAnimation(this, R.anim.slide_down)
        binding.coordinatorLayout.startAnimation(animationZoomOut)

        //Getting Latitude & Longitude From Location through SplashActivity
        val lat = intent.getStringExtra("lat")
        val long = intent.getStringExtra("long")

        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        getJsonData(lat!!, long!!)

    }

    private fun getJsonData(lat: String, long: String)
    {
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
            .uppercase(Locale.getDefault())

        val list = response.getJSONArray("list")
        val jsonArray = list.getJSONObject(0)
        val temp = jsonArray.getJSONObject("temp").getString("day")
//        tempr = ((((tempr).toFloat() - 273.15)).toInt()).toString()
        binding.temp.text = "${temp}°"
        binding.weather.text = jsonArray.getJSONArray("weather").getJSONObject(0).getString("main")
        binding.weatherDescription.text = jsonArray.getJSONArray("weather").getJSONObject(0).getString("description")
        val iconID = jsonArray.getJSONArray("weather").getJSONObject(0).getString("icon")

        val graphTempList: ArrayList<Int> = ArrayList()

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
            val weatherIcon = dataWeather.getString("icon")
            graphTempList.add(tempInList.getInt("day"))
            viewModel.mWeatherItemList.add(WeatherItems(transformedDate, tempAvr, weatherIcon))
//            weatherItemList.add(WeatherItems(transformedDate, tempAvr, weather))
        }
        val recyclerView: RecyclerView = findViewById(R.id.recycleView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter =WeatherAdapter(viewModel.mWeatherItemList)
        recyclerView.adapter =adapter


        val aaChartView = findViewById<AAChartView>(R.id.aa_chart_view)
        val aaChartModel : AAChartModel = AAChartModel()
            .chartType(AAChartType.Line)
            .yAxisTitle("temp")
            .xAxisVisible(false)
            .yAxisVisible(false)
            .backgroundColor("#00000000")
            .dataLabelsEnabled(true)
            .series(arrayOf(
                AASeriesElement()
                    .name(response.getJSONObject("city").getString("name"))
                    .data(graphTempList.toArray())
            )
            )
        aaChartView.aa_drawChartWithChartModel(aaChartModel)
    }
}
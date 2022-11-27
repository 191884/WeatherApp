package com.yo.weatherapp

class WeatherItems(
    private val mDate: String,
    private val mTempAvr: Double,
    private val mWeather: String
) {
    fun getmDate(): String {
        return mDate
    }

    fun getmTempAvr(): Double {
        return mTempAvr
    }

    fun getmWeather(): String {
        return mWeather
    }
}

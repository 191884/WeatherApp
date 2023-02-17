package com.snappy.weathermate

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

class SplashActivity : AppCompatActivity() {
    private lateinit var mfusedlocation: FusedLocationProviderClient
    private var myRequestCode=1010

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mfusedlocation= LocationServices.getFusedLocationProviderClient(this)

        getLastLocation()
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermission()) {
            if (locationEnable()) {
                mfusedlocation.lastLocation.addOnCompleteListener { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        newLocation()
                    } else {
                        Handler(Looper.getMainLooper()).postDelayed({
                            val intent = Intent(this, MainActivity::class.java)
                            Log.e("Lat and Long", "${location.latitude} ${location.longitude}")
                            intent.putExtra("lat", location.latitude.toString())
                            intent.putExtra("long", location.longitude.toString())
                            startActivity(intent)
                            finish()
                        }, 1000)
                    }
                }
            } else {
                Toast.makeText(this, "Please Turn on your GPS location", Toast.LENGTH_LONG).show()
            }
        } else {
            requestPermission()
        }
    }

    //    @SuppressLint("MissingPermission")
    @SuppressLint("MissingPermission")
    private fun newLocation() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
            .setWaitForAccurateLocation(false)
            .setIntervalMillis(0)
            .setMaxUpdateDelayMillis(0)
            .build()
//        val locationRequest= LocationRequest.create()?.apply {
//            interval=0
//            priority=LocationRequest.PRIORITY_HIGH_ACCURACY
//            fastestInterval=0
//            numUpdates=1
//        }
        mfusedlocation= LocationServices.getFusedLocationProviderClient(this@SplashActivity)
        mfusedlocation.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    private val locationCallback=object: LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            var lastLocation: Location? =p0.lastLocation
        }
    }

    private fun locationEnable(): Boolean {
        val locationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION),myRequestCode)
    }

    private fun checkPermission(): Boolean {
        if(
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED
        ){
            return true
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==myRequestCode)
        {
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                getLastLocation()
            }
        }
    }
}
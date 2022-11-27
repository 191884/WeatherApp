package com.yo.weatherapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Getting Latitude & Longitude From Location through SplashActivity
        val lat = intent.getStringExtra("lat")
        val long = intent.getStringExtra("long")

        //Passing Latitude & Longitude to Fragment
        val myFragment = MainFragment()
        val mBundle = Bundle()
        mBundle.putString("lat",lat)
        mBundle.putString("long",long)
        myFragment.arguments= mBundle
        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction().add(R.id.coordinatorLayout,myFragment).commit()
        }
        fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
            beginTransaction().func().commit()
        }

    }
}
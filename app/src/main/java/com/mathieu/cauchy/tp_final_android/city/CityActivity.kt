package com.mathieu.cauchy.tp_final_android.city

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mathieu.cauchy.tp_final_android.R
import com.mathieu.cauchy.tp_final_android.weather.WeatherActivity
import com.mathieu.cauchy.tp_final_android.weather.WeatherFragment

class CityActivity : AppCompatActivity(), CityFragment.CityFragmentListener {
    private lateinit var cityFragment: CityFragment
    private var currentCity: City? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)
        cityFragment = supportFragmentManager.findFragmentById(R.id.city_fragment) as CityFragment
        cityFragment.listener = this
    }

    override fun onCitySelected(city: City) {
        currentCity = city
        startWeatherActivity(city)
    }

    private fun startWeatherActivity(city: City) {
        val intent = Intent(this, WeatherActivity::class.java)
        intent.putExtra(WeatherFragment.EXTRA_CITY_NAME, city.name)
        startActivity(intent)
    }
}
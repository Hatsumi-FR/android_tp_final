package com.mathieu.cauchy.tp_final_android.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mathieu.cauchy.tp_final_android.App
import com.mathieu.cauchy.tp_final_android.R
import com.mathieu.cauchy.tp_final_android.openweathermap.WeatherWrapper
import com.mathieu.cauchy.tp_final_android.openweathermap.mapOpenWeatherDataToWeather
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_weather.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherFragment : Fragment() {
    private lateinit var cityName: String


    private lateinit var refreshLayout : SwipeRefreshLayout
    private lateinit var city : TextView
    private lateinit var weatherIcon : ImageView
    private lateinit var weatheDescription : TextView
    private lateinit var temperature : TextView
    private lateinit var humidity : TextView
    private lateinit var pressure : TextView

    companion object {
        val EXTRA_CITY_NAME = "com.mathieu.cauchy.tp_final_android.weather.extras.EXTRA_CITY_NAME"
        fun newInstance() = WeatherFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        refreshLayout = view.findViewById(R.id.swipe_refresh)
        city = view.findViewById(R.id.city)
        weatherIcon = view.findViewById(R.id.weather_icon)
        weatheDescription = view.findViewById(R.id.weather_description)
        temperature = view.findViewById(R.id.temperature)
        humidity = view.findViewById(R.id.humidity)
        pressure = view.findViewById(R.id.pressure)
        refreshLayout.setOnRefreshListener { refreshWeather() }
        return view
    }

    private fun refreshWeather() {
        updateWeatherForCity(cityName)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(activity?.intent!!.hasExtra(EXTRA_CITY_NAME)){
            updateWeatherForCity(activity!!.intent.getStringExtra(EXTRA_CITY_NAME)!!)
        }
    }

    private fun updateWeatherForCity(cityName: String) {
        this.cityName = cityName
        this.city.text = cityName

        if (!refreshLayout.isRefreshing){
            refreshLayout.isRefreshing = true
        }

        val call = App.weatherService.getWeather("$cityName,fr")
        call.enqueue(object : Callback<WeatherWrapper>{
            override fun onResponse(
                call: Call<WeatherWrapper>,
                response: Response<WeatherWrapper>
            ) {
                Log.i("Un tag", "ok ${response.body()}")
                response?.body()?.let {
                   val weather = mapOpenWeatherDataToWeather(it)
                    Log.i("razeifzea", "ah ué : $weather")
                    updateUi(weather)
                }
                refreshLayout.isRefreshing = false
            }
            override fun onFailure(call: Call<WeatherWrapper>, t: Throwable) {
                Log.e("Un tag", "faield", t)
                Toast.makeText(activity, "error", Toast.LENGTH_SHORT).show()
                refreshLayout.isRefreshing = false
            }
        })
    }

    private fun updateUi(weather: Weather) {
        Picasso.get().load(weather.iconUrl).placeholder(R.drawable.gopher).into(weatherIcon)

        weatheDescription.text = weather.description
        temperature.text = getString(R.string.weather_temperature_value, weather.temperature.toInt())
        humidity.text = getString(R.string.weather_humidity_value, weather.humidity)
        pressure.text = getString(R.string.weather_pressure_value, weather.pressure)
    }

}
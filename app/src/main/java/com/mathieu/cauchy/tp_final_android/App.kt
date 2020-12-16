package com.mathieu.cauchy.tp_final_android

import android.app.Application
import com.mathieu.cauchy.tp_final_android.openweathermap.OpenWeatherService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    companion object {
        lateinit var instance : App
        val database : Database by lazy {
            Database(instance)
        }
        private val httpClient = OkHttpClient.Builder().build()
        private val retrofit = Retrofit.Builder().client(httpClient).baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val weatherService : OpenWeatherService = retrofit.create(OpenWeatherService::class.java)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
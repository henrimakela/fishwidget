package fi.henrimakela.fishwidget.data.network

import WeatherResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://api.openweathermap.org/data/2.5/onecall?lat=65.000094&lon=25.508546&lang=fi&exclude=minutely,hourly,daily,alerts&units=metric&appid=
interface WeatherService {
    @GET("data/2.5/onecall")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String = "minutely,hourly,daily,alerts",
        @Query("units") units: String = "metric",
        @Query("appid") appid: String): WeatherResponse

    companion object{
        fun create(): WeatherService{
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create<WeatherService>(WeatherService::class.java)
        }
    }
}
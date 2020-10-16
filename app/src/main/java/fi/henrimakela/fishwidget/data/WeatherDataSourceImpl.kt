package fi.henrimakela.fishwidget.data

import WeatherResponse
import com.google.android.gms.location.FusedLocationProviderClient
import fi.henrimakela.data.WeatherDataSource
import fi.henrimakela.fishwidget.BuildConfig
import fi.henrimakela.fishwidget.data.network.WeatherService

class WeatherDataSourceImpl(private val service: WeatherService) : WeatherDataSource{

    override suspend fun getWeather(lat: Double, lon: Double): WeatherResponse {
        return service.getWeather(lat, lon, appid = BuildConfig.OPENWEATHERMAP_API_KEY)
    }

}
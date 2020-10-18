package fi.henrimakela.fishwidget.data

import WeatherResponse
import com.google.android.gms.location.FusedLocationProviderClient
import fi.henrimakela.data.WeatherDataSource
import fi.henrimakela.domain.Resource
import fi.henrimakela.fishwidget.BuildConfig
import fi.henrimakela.fishwidget.data.network.ResponseHandler
import fi.henrimakela.fishwidget.data.network.WeatherService

class WeatherDataSourceImpl(private val service: WeatherService, private val responseHandler: ResponseHandler) : WeatherDataSource{

    override suspend fun getWeather(lat: Double, lon: Double): Resource<WeatherResponse> {
        return try {
            responseHandler.handleSuccess(service.getWeather(lat, lon, appid = BuildConfig.OPENWEATHERMAP_API_KEY))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

}
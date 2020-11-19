package fi.henrimakela.fishwidget.data


import WeatherResponse
import fi.henrimakela.data.WeatherDataSource
import fi.henrimakela.domain.Resource
import fi.henrimakela.fishwidget.BuildConfig
import fi.henrimakela.fishwidget.data.network.ResponseHandler
import fi.henrimakela.fishwidget.data.network.WeatherService
import java.util.*

class WeatherDataSourceImpl(
    private val service: WeatherService,
    private val responseHandler: ResponseHandler
) : WeatherDataSource {

    override suspend fun getWeather(lat: Double, lon: Double, unit: String): Resource<WeatherResponse> {
        return try {
            responseHandler.handleSuccess(
                service.getWeather(
                    lat,
                    lon,
                    lang = Locale.getDefault().language,
                    units = unit,
                    appid = BuildConfig.OPENWEATHERMAP_API_KEY
                )
            )
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

}
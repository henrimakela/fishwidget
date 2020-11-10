package fi.henrimakela.data.repository

import WeatherResponse
import fi.henrimakela.domain.fish.FishForecast
import fi.henrimakela.domain.fish.FishPredictionConfiguration
import java.beans.Visibility

//http://www.kalassa.net/kalapedia/index.php/S%C3%A4%C3%A4n_vaikutus_kalastukseen
object FishPredictor {

    private val ICON_BASE_URL = "http://openweathermap.org/img/wn/"
    private lateinit var configuration: FishPredictionConfiguration

    fun getPrediction(
        weather: WeatherResponse,
        configuration: FishPredictionConfiguration
    ): FishForecast {
        this.configuration = configuration

        var forecast = FishForecast(
            getFishWeatherDesc(weather.current.weather[0].id),
            getPressureDesc(weather.current.pressure),
            getWindDesc(weather.current.wind_speed),
            weather.current.weather[0].main,
            "$ICON_BASE_URL${weather.current.weather[0].icon}.png",
            weather.current.temp,
            weather.current.feels_like,
            weather.current.humidity,
            weather.current.pressure,
            weather.current.wind_speed,
            weather.current.wind_deg
        )
        return forecast
    }

    private fun getWindDesc(windSpeed: Double): String {
        if (windSpeed > 4) {
            return configuration.wind
        }
        return configuration.noWind
    }


    private fun getPressureDesc(pressure: Double): String {
        return when {
            pressure < 1013.25 -> {
                configuration.lowPressure
            }
            else -> {
                configuration.steadyPressure
            }
        }
    }

    private fun getFishWeatherDesc(id: Int): String {
        return when {
            id in 500..501 -> {
                configuration.rainNormal
            }
            id in 502..531 -> {
                configuration.rainHeavy
            }
            id in 801..804 -> {
                configuration.visibilityCloudiness
            }
            id == 800 -> {
                configuration.visibilityClearSky
            }
            else -> {
                configuration.visibilityCloudiness
            }
        }
    }
}

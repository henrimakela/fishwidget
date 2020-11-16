package fi.henrimakela.data.repository

import WeatherResponse
import fi.henrimakela.domain.fish.FishForecast
import fi.henrimakela.domain.fish.FishPredictionConfiguration

//http://www.kalassa.net/kalapedia/index.php/S%C3%A4%C3%A4n_vaikutus_kalastukseen
object FishPredictor {

    private val ICON_BASE_URL = "http://openweathermap.org/img/wn/"
    private lateinit var configuration: FishPredictionConfiguration

    fun getPrediction(
        response: WeatherResponse,
        configuration: FishPredictionConfiguration
    ): FishForecast {
        this.configuration = configuration

        var forecast = FishForecast(
            getFishWeatherDesc(response.weather[0].id),
            getPressureDesc(response.main.pressure),
            getWindDesc(response.wind.speed),
            response.weather[0].main,
            "$ICON_BASE_URL${response.weather[0].icon}.png",
            response.main.temp,
            response.main.feels_like,
            response.main.humidity,
            response.main.pressure,
            response.wind.speed,
            response.wind.deg
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
            pressure < 1013 -> {
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

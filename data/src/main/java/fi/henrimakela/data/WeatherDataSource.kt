package fi.henrimakela.data

import WeatherResponse
import fi.henrimakela.domain.Resource

interface WeatherDataSource {
    suspend fun getWeather(lat: Double, lon: Double, unit: String = "metric"): Resource<WeatherResponse>
}
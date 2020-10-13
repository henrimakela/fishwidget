package fi.henrimakela.data

import WeatherResponse


interface WeatherDataSource {
    suspend fun getWeather(lat: Double, lon: Double): WeatherResponse
}
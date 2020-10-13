package fi.henrimakela.usecases

import fi.henrimakela.data.repository.WeatherRepository


class GetWeather(private val lat: Double, private val lon: Double, private val repository: WeatherRepository) {
    suspend operator fun invoke() = repository.getWeather(lat, lon)
}
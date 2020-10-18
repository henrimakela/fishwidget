package fi.henrimakela.usecases

import fi.henrimakela.data.repository.WeatherRepository


class GetWeather(private val repository: WeatherRepository) {


    suspend operator fun invoke(lat: Double,  lon: Double) = repository.getWeather(lat, lon)
}
package fi.henrimakela.data

import Weather
import WeatherResponse
import fi.henrimakela.domain.fish.FishForecast

interface FishingDataSource {
    fun getFishForecast(weather: WeatherResponse): FishForecast
}
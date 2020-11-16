package fi.henrimakela.data

import WeatherResponse
import fi.henrimakela.domain.fish.FishForecast

interface FishingDataSource {
    fun getFishForecast(weather: WeatherResponse): FishForecast
}
package fi.henrimakela.data

import Weather
import WeatherResponse
import fi.henrimakela.domain.fish.FishForecast
import fi.henrimakela.domain.fish.FishPredictionConfiguration

interface FishingDataSource {
    fun getFishForecast(weather: WeatherResponse): FishForecast
}
package fi.henrimakela.data

import Weather
import fi.henrimakela.domain.fish.FishForecast

interface FishForecastDataSource {
    fun getFishForecast(weather: Weather): FishForecast
}
package fi.henrimakela.data.repository

import WeatherResponse
import fi.henrimakela.data.FishingDataSource
import fi.henrimakela.domain.fish.FishForecast
import fi.henrimakela.domain.fish.FishPredictionConfiguration

class FishingDataRepository(private val dataSource: FishingDataSource) {
    suspend fun getFishForecast(weather: WeatherResponse): FishForecast {
        val forecast = dataSource.getFishForecast(weather)
        return forecast
    }
}
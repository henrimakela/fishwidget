package fi.henrimakela.usecases

import WeatherResponse
import fi.henrimakela.data.FishingDataSource
import fi.henrimakela.data.repository.FishingDataRepository

class GetFishForecast(private val repository: FishingDataRepository){
    suspend operator fun invoke(weather: WeatherResponse) = repository.getFishForecast(weather)
}
package fi.henrimakela.usecases

import WeatherResponse
import fi.henrimakela.data.FishingDataSource

class GetFishForecast(private val dataSource: FishingDataSource){

    suspend operator fun invoke(weather: WeatherResponse) = dataSource.getFishForecast(weather)
}
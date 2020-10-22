package fi.henrimakela.data.repository

import WeatherResponse
import fi.henrimakela.data.FishingDataSource
import fi.henrimakela.domain.fish.FishForecast

class FishingDataRepository() : FishingDataSource {

    override fun getFishForecast(weather: WeatherResponse): FishForecast {
        return FishPredictor.getPrediction(weather)
    }

}
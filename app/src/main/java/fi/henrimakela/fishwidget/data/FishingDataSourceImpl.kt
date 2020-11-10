package fi.henrimakela.fishwidget.data

import WeatherResponse
import android.content.Context
import fi.henrimakela.data.FishingDataSource
import fi.henrimakela.data.repository.FishPredictor
import fi.henrimakela.domain.fish.FishForecast
import fi.henrimakela.domain.fish.fishPredictionConfiguration
import fi.henrimakela.fishwidget.R

class FishingDataSourceImpl(context: Context) : FishingDataSource {
    
    private val configuration = fishPredictionConfiguration { 
        lowPressure{
            context.getString(R.string.prediction_low_pressure)
        }
        steadyPressure {
            context.getString(R.string.prediction_steady_pressure)
        }
        visibilityClearSky{
            context.getString(R.string.prediction_clear_sky)
        }
        visibilityCloudiness {
            context.getString(R.string.prediction_cloudiness)
        }
        visibilityNightFall {
            context.getString(R.string.prediction_nightfall)
        }
        rainNormal {
            context.getString(R.string.prediction_rain_normal)
        }
        rainHeavy {
            context.getString(R.string.prediction_rain_heavy)
        }
        waterTemp {
            context.getString(R.string.prediction_water_temp)
        }
        wind {
            context.getString(R.string.prediction_wind_normal)
        }
        noWind {
            context.getString(R.string.prediction_no_wind)
        }
    }
    override fun getFishForecast(
        weather: WeatherResponse
    ): FishForecast {
        return FishPredictor.getPrediction(weather, configuration)
    }
}
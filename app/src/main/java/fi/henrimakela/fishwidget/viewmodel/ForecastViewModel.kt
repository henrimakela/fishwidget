package fi.henrimakela.fishwidget.viewmodel

import WeatherResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fi.henrimakela.data.repository.WeatherRepository
import fi.henrimakela.fishwidget.data.WeatherDataSourceImpl
import fi.henrimakela.fishwidget.data.network.WeatherService
import fi.henrimakela.usecases.GetWeather
import kotlinx.coroutines.launch

class ForecastViewModel : ViewModel() {


    private var _weatherResponse = MutableLiveData<WeatherResponse>()
    val weatherResponse: LiveData<WeatherResponse> get() = _weatherResponse

    init {
        loadWeather()
    }

    private fun loadWeather() {
        viewModelScope.launch {
            //_weatherResponse.postValue(getWeather())
        }
    }

    fun getWeatherWithCoordinates(lat: Double, lon: Double) {
        viewModelScope.launch {
            _weatherResponse.postValue(
                GetWeather(
                    65.012615, 25.471453, WeatherRepository(
                        WeatherDataSourceImpl(
                            WeatherService.create()
                        )
                    )
                ).invoke()
            )
        }
    }
}
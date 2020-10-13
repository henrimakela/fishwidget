package fi.henrimakela.fishwidget

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fi.henrimakela.data.repository.WeatherRepository
import fi.henrimakela.fishwidget.data.WeatherDataSourceImpl
import fi.henrimakela.fishwidget.data.network.WeatherService
import fi.henrimakela.usecases.GetWeather
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var getWeather = GetWeather(
            65.012615, 25.471453, WeatherRepository(
                WeatherDataSourceImpl(
                    WeatherService.create()
                )
            )
        )

        GlobalScope.launch {
            var response = getWeather()
            println("RESPONSSI: $response")
        }


    }
}
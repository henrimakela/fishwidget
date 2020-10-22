package fi.henrimakela.fishwidget.di

import fi.henrimakela.data.FishingDataSource
import fi.henrimakela.data.WeatherDataSource
import fi.henrimakela.data.repository.FishingDataRepository
import fi.henrimakela.data.repository.WeatherRepository
import fi.henrimakela.fishwidget.data.WeatherDataSourceImpl
import fi.henrimakela.fishwidget.data.network.ResponseHandler
import fi.henrimakela.fishwidget.data.network.WeatherService
import fi.henrimakela.usecases.GetFishForecast
import fi.henrimakela.usecases.GetWeather
import org.koin.dsl.module

val appModule = module(override = true) {


    single {ResponseHandler()}
    single {WeatherService.create()}
    single {WeatherDataSourceImpl(get(), get()) as WeatherDataSource}
    single {WeatherRepository(get()) }
    single {FishingDataRepository() as FishingDataSource}
    single {GetFishForecast(get())}
    single {GetWeather(get())}

}
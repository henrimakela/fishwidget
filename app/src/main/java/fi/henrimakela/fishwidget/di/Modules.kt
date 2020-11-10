package fi.henrimakela.fishwidget.di

import fi.henrimakela.data.FishingDataSource
import fi.henrimakela.data.WeatherDataSource
import fi.henrimakela.data.repository.FishingDataRepository
import fi.henrimakela.data.repository.WeatherRepository
import fi.henrimakela.fishwidget.data.FishingDataSourceImpl
import fi.henrimakela.fishwidget.data.WeatherDataSourceImpl
import fi.henrimakela.fishwidget.data.network.ResponseHandler
import fi.henrimakela.fishwidget.data.network.WeatherService
import fi.henrimakela.usecases.GetFishForecast
import fi.henrimakela.usecases.GetWeather
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module(override = true) {

    single {ResponseHandler()}
    single {WeatherService.create()}
    single {WeatherDataSourceImpl(get(), get()) as WeatherDataSource}
    single {FishingDataSourceImpl(get()) as FishingDataSource}
    single {WeatherRepository(get()) }
    single {FishingDataRepository(get())}
    single {GetFishForecast(get())}
    single {GetWeather(get())}
}
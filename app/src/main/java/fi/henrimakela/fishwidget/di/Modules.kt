package fi.henrimakela.fishwidget.di

import fi.henrimakela.data.WeatherDataSource
import fi.henrimakela.data.repository.WeatherRepository
import fi.henrimakela.fishwidget.data.WeatherDataSourceImpl
import fi.henrimakela.fishwidget.data.network.WeatherService
import org.koin.dsl.module

val appModule = module(override = true) {

    single {WeatherService.create()}
    single {WeatherDataSourceImpl(get()) as WeatherDataSource}
    single {WeatherRepository(get()) }

}
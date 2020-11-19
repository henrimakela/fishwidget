package fi.henrimakela.fishwidget.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import fi.henrimakela.fishwidget.util.AppSettingPreferences
import org.koin.core.KoinComponent
import org.koin.core.inject
import fi.henrimakela.fishwidget.view.*

/**
 * A wrapper view model for app settings, shared between [SettingsFragment] and [ForecastFragment]
 * @property settings App settings or preferences stored in shared preferences
 * @property unit immutable live data
 * */
class SettingsViewModel : ViewModel(), KoinComponent {

    private val settings: AppSettingPreferences by inject()
    val unit: LiveData<String> = settings.unit

    fun saveUnitPreference(unit: String){
        settings.saveWeatherDataUnitPreference(unit)
    }
}
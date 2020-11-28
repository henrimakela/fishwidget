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
 * @property unit immutable live data for unit preference
 * @property useDarkMode immutable live data for dark mode preference
 * */

class SettingsViewModel : ViewModel(), KoinComponent {

    private val settings: AppSettingPreferences by inject()
    val unit: LiveData<String> = settings.unit
    val useDarkMode: LiveData<Boolean> = settings.useDarkMode

    fun saveUnitPreference(unit: String){
        settings.saveWeatherDataUnitPreference(unit)
    }
    fun saveThemeModePreference(useDarkMode: Boolean){
        settings.saveThemeModePreference(useDarkMode)
    }
}
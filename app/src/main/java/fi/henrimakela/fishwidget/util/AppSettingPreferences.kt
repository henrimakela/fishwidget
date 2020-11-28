package fi.henrimakela.fishwidget.util

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fi.henrimakela.fishwidget.R

/**
 * Helper class for getting weather unit preferences from shared preferences.
 * @property _unit private mutable live data. Will be changed from within the class
 * @property unit public getter of immutable live data for the unit
 *
 */

class AppSettingPreferences(context: Context) :
    SharedPreferences.OnSharedPreferenceChangeListener {

    companion object {
        private val PREFERENCE_UNIT_KEY = "unit"
        private val PREFERENCE_THEME_KEY = "theme"
        val PREFERENCE_UNIT_METRIC = "metric"
        val PREFERENCE_UNIT_IMPERIAL = "imperial"
    }

    private val sharedPreferences =
        context.getSharedPreferences(
            context.getString(R.string.fishwidget_settings_preferences),
            Context.MODE_PRIVATE
        )
    private var _unit = MutableLiveData<String>()
    private var _useDarkMode = MutableLiveData<Boolean>()

    val unit: LiveData<String> get() = _unit
    val useDarkMode: LiveData<Boolean> get() = _useDarkMode


    init {
        _unit.postValue(getWeatherDataUnitPreference())
        _useDarkMode.postValue(isUsingDarkMode())
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    fun unregisterSharedPreferencesListener(){
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    fun saveWeatherDataUnitPreference(unit: String) {
        _unit.postValue(unit)
        sharedPreferences.edit().putString(PREFERENCE_UNIT_KEY, unit).apply()
    }

    fun saveThemeModePreference(useDarkMode: Boolean) {
        sharedPreferences.edit().putBoolean(PREFERENCE_THEME_KEY, useDarkMode).apply()
        _useDarkMode.postValue(useDarkMode)
    }

    private fun getWeatherDataUnitPreference(): String? {
        return sharedPreferences.getString(PREFERENCE_UNIT_KEY, PREFERENCE_UNIT_METRIC)
    }

    fun isUsingDarkMode(): Boolean {
        return sharedPreferences.getBoolean(PREFERENCE_THEME_KEY, false)
    }

    fun noSettingsConfigured(): Boolean{
        return sharedPreferences.all.isEmpty()
    }

    override fun onSharedPreferenceChanged(prefs: SharedPreferences?, key: String?) {
        key?.let {
            when (it) {
                PREFERENCE_UNIT_KEY -> {
                    prefs?.let {
                        _unit.postValue(it.getString(key, PREFERENCE_UNIT_METRIC))
                    }
                }
                PREFERENCE_THEME_KEY -> {
                    prefs?.let {
                        _useDarkMode.postValue(it.getBoolean(key, false))
                    }
                }
                else -> {
                }
            }
        }
    }

}
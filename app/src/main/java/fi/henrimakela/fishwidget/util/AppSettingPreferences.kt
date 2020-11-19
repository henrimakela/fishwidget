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

    companion object{
        private val PREFERENCE_UNIT_KEY = "unit"
        val PREFERENCE_UNIT_METRIC = "metric"
        val PREFERENCE_UNIT_IMPERIAL = "imperial"
    }
    private val sharedPreferences =
        context.getSharedPreferences(
            context.getString(R.string.fishwidget_settings_preferences),
            Context.MODE_PRIVATE
        )
    private var _unit = MutableLiveData<String>()
    val unit: LiveData<String> get() = _unit

    init {
        _unit.postValue(getWeatherDataUnitPreference())
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    fun saveWeatherDataUnitPreference(unit: String) {
        sharedPreferences.edit().putString(PREFERENCE_UNIT_KEY, unit).apply()
    }

    private fun getWeatherDataUnitPreference(): String? {
        return sharedPreferences.getString(PREFERENCE_UNIT_KEY, PREFERENCE_UNIT_METRIC)
    }

    override fun onSharedPreferenceChanged(prefs: SharedPreferences?, key: String?) {
        key?.let {
            if (it == PREFERENCE_UNIT_KEY) {
                prefs?.let {
                    _unit.postValue(it.getString(key, PREFERENCE_UNIT_METRIC))
                }
            }
        }
    }

}
package fi.henrimakela.fishwidget

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import fi.henrimakela.fishwidget.util.AppSettingPreferences
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainActivity : AppCompatActivity(), KoinComponent {
    val settings: AppSettingPreferences by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setAppThemeByPreference()

        settings.useDarkMode.observe(this, Observer {
            if(it){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        })
    }

    private fun setAppThemeByPreference() {
        val currentThemeMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (settings.noSettingsConfigured()) {
            settings.saveThemeModePreference(currentThemeMode == Configuration.UI_MODE_NIGHT_YES)
        } else {
            when (settings.isUsingDarkMode()) {
                true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        settings.unregisterSharedPreferencesListener()
    }
}
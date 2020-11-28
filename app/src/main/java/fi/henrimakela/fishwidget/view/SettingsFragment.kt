/*
 * Fishwidget
 *
 * Copyright (c) 2020. Henri Mäkelä www.henrimakela.fi
 */

package fi.henrimakela.fishwidget.view

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import fi.henrimakela.fishwidget.R
import fi.henrimakela.fishwidget.util.AppSettingPreferences
import fi.henrimakela.fishwidget.viewmodel.SettingsViewModel
import kotlinx.android.synthetic.main.fragment_settings.*

/**
 * Fragment for app settings. Currently, the only setting is the unit of the weather data
 * */

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var settingsViewModel: SettingsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsViewModel =
            ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.main_nav_graph)).get(
                SettingsViewModel::class.java
            )
        setupToolbar()
        setupUnitSwitch()
        setupThemeSwitch()

       /* settingsViewModel.useDarkMode.observe(viewLifecycleOwner, Observer {
            when{
                it -> {
                    theme_switch.isChecked = true
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                else -> {
                    theme_switch.isChecked = false
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        })*/
    }

    private fun setupUnitSwitch() {
        settingsViewModel.unit.observe(viewLifecycleOwner, Observer {
            unit_switch.apply {
                when {
                    it == AppSettingPreferences.PREFERENCE_UNIT_METRIC -> {
                        text = resources.getString(R.string.unit_metric)
                        isChecked = false
                    }
                    it == AppSettingPreferences.PREFERENCE_UNIT_IMPERIAL -> {
                        text = resources.getString(R.string.unit_imperial)
                        isChecked = true
                    }
                }
            }
        })
        unit_switch.setOnCheckedChangeListener { _, checked ->
            when {
                checked -> settingsViewModel.saveUnitPreference(AppSettingPreferences.PREFERENCE_UNIT_IMPERIAL)
                else -> settingsViewModel.saveUnitPreference(AppSettingPreferences.PREFERENCE_UNIT_METRIC)
            }
        }
    }

    private fun setupThemeSwitch() {
        setThemeSwitchInitialState()
        theme_switch.setOnCheckedChangeListener { _, checked ->
            when{
                checked -> settingsViewModel.saveThemeModePreference(true)
                else -> settingsViewModel.saveThemeModePreference(false)
            }

        }
    }

    private fun setupToolbar() = toolbar.apply {
        setNavigationIcon(R.drawable.ic_back)
        title = resources.getString(R.string.settings)
        setNavigationOnClickListener { requireView().findNavController().popBackStack() }
    }

    private fun setThemeSwitchInitialState() {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_YES -> theme_switch.isChecked = true
            else -> theme_switch.isChecked = false
        }
    }
}
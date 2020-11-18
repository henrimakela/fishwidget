package fi.henrimakela.fishwidget.view

import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import fi.henrimakela.fishwidget.R
import fi.henrimakela.fishwidget.util.AppSettingPreferences
import fi.henrimakela.fishwidget.viewmodel.ForecastViewModel
import fi.henrimakela.fishwidget.viewmodel.SettingsViewModel
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var settingsViewModel: SettingsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsViewModel =
            ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.main_nav_graph)).get(
                SettingsViewModel::class.java
            )
        setupToolbar()
        setupSwitch()
    }

    private fun setupSwitch() {
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
        unit_switch.apply {
            setOnCheckedChangeListener { _, checked ->
                when {
                    checked -> settingsViewModel.saveUnitPreference(AppSettingPreferences.PREFERENCE_UNIT_IMPERIAL)
                    else -> settingsViewModel.saveUnitPreference(AppSettingPreferences.PREFERENCE_UNIT_METRIC)
                }
            }
        }

    }

    private fun setupToolbar() = toolbar.apply {
        setNavigationIcon(R.drawable.ic_back)
        title = resources.getString(R.string.settings)
        setNavigationOnClickListener { requireView().findNavController().popBackStack() }
    }
}
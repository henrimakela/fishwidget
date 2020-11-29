/*
 * Fishwidget
 *
 * Copyright (c) 2020. Henri Mäkelä www.henrimakela.fi
 */

package fi.henrimakela.fishwidget.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import fi.henrimakela.domain.Status
import fi.henrimakela.domain.fish.FishForecast
import fi.henrimakela.fishwidget.R
import fi.henrimakela.fishwidget.adapter.ForecastAdapter
import fi.henrimakela.fishwidget.util.getWindDegString
import fi.henrimakela.fishwidget.viewmodel.ForecastViewModel
import fi.henrimakela.fishwidget.viewmodel.SettingsViewModel
import kotlinx.android.synthetic.main.fragment_forecast.*

/**
 * The main view of the application
 * @property forecastViewModel the view model that provides all the forecast related data
 * @property settingsViewModel the view model that provides the unit that should be used when fetching the weather (metric or imperial) default is metric.
 *
 * */
class ForecastFragment : Fragment() {

    private lateinit var forecastViewModel: ForecastViewModel
    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var adapter: ForecastAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupRecyclerView()
        setupViewModels()
        observeData()

        weather_details.setOnClickListener {
            findNavController().navigate(R.id.action_forecastFragment_to_weatherDetailFragment)
        }
        refresh.setOnClickListener {
            settingsViewModel.unit.value?.let {
                getForecastWithUserCoordinates(it)
            }
        }
    }

    private fun setupToolbar() {
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings -> {
                    findNavController().navigate(R.id.action_forecastFragment_to_settingsFragment)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun observeData() {
        settingsViewModel.unit.observe(viewLifecycleOwner, Observer { getForecastWithUserCoordinates(it)})
        forecastViewModel.fishForecast.observe(viewLifecycleOwner, Observer {updateViews(it)})
        forecastViewModel.isLoading.observe(viewLifecycleOwner, Observer { showOrHideLoading(it) })

        forecastViewModel.weatherResponse.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.ERROR) {
                showError(it.message!!)
            }
        })

    }

    private fun setupViewModels() {
        forecastViewModel =
            ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.main_nav_graph)).get(
                ForecastViewModel::class.java
            )
        settingsViewModel =
            ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.main_nav_graph)).get(
                SettingsViewModel::class.java
            )
    }

    private fun setupRecyclerView() {
        adapter = ForecastAdapter()
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun showOrHideLoading(isLoading: Boolean) {
        if (isLoading) {
            arrayOf(temperature, weather_description).forEach {
                it.visibility = View.INVISIBLE
            }
            progress_indicator.visibility = View.VISIBLE
        } else {
            progress_indicator.visibility = View.GONE
        }
    }

    private fun showError(message: String) {
        error_description.visibility = View.VISIBLE
        error_description.text = message
    }

    private fun updateViews(data: FishForecast) {

        arrayOf(error_description, progress_indicator).forEach {
            it.visibility = View.INVISIBLE
        }

        arrayOf(temperature, weather_description).forEach {
            it.visibility = View.VISIBLE
        }

        temperature.text = "${data.temp.toInt()}°"
        weather_description.text = data.main
        wind.text = "${data.wind_speed.toInt()} m/s"
        wind_deg.text = getWindDegString(data.wind_deg, requireContext())
        humidity.text = "${data.humidity.toInt()}%"
        updateList(data)
    }

    private fun updateList(data: FishForecast) {
        var overAllWeatherItemData = ListItemData(
            R.drawable.ic_fish,
            getString(R.string.overall_weather_title),
            data.description_fish_weather
        )

        var windItemData = ListItemData(
            R.drawable.ic_wind,
            getString(R.string.wind_title),
            data.description_wind
        )

        var pressureItemData = ListItemData(
            R.drawable.ic_barometer,
            getString(R.string.air_pressure_title),
            data.description_pressure
        )

        adapter.setData(arrayListOf(overAllWeatherItemData, windItemData, pressureItemData))
    }

    private fun getForecastWithUserCoordinates(unit: String) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        if (checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                forecastViewModel.getForecastWithCoordinates(it.latitude, it.longitude, unit)
            }

            //TODO: Handle failure
        }
    }

}
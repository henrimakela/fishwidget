package fi.henrimakela.fishwidget.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.squareup.picasso.Picasso
import fi.henrimakela.domain.Status
import fi.henrimakela.domain.fish.FishForecast
import fi.henrimakela.fishwidget.R
import fi.henrimakela.fishwidget.adapter.ForecastAdapter
import fi.henrimakela.fishwidget.viewmodel.ForecastViewModel
import kotlinx.android.synthetic.main.fragment_forecast.*

class ForecastFragment : Fragment() {

    private lateinit var forecastViewModel: ForecastViewModel
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

        adapter = ForecastAdapter()
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())

        forecastViewModel =
            ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.main_nav_graph)).get(
                ForecastViewModel::class.java
            )
        getForecastWithUserCoordinates()
        Picasso.get().setLoggingEnabled(true)
        forecastViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            showOrHideLoading(it)
        })


        forecastViewModel.weatherResponse.observe(viewLifecycleOwner, Observer {
            if(it.status == Status.ERROR){
                showError(it.message!!)
            }

        })

        forecastViewModel.fishForecast.observe(viewLifecycleOwner, Observer {
            updateViews(it)
        }
        )

        refresh.setOnClickListener {
            getForecastWithUserCoordinates()
        }
    }

    private fun showOrHideLoading(isLoading: Boolean) {
        if(isLoading){
            arrayOf(temperature, weather_description, weather_icon).forEach {
                it.visibility = View.INVISIBLE
            }
            progress_indicator.visibility = View.VISIBLE
        }
        else{
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

        arrayOf(temperature, weather_description, weather_icon).forEach {
            it.visibility = View.VISIBLE
        }

        temperature.text = "${data.temp.toInt()}°"
        weather_description.text = data.main
        wind.text = "${data.wind_speed.toInt()} m/s"
        feels.text = "${data.feels_like.toInt()}°"
        humidity.text = "${data.humidity.toInt()}%"
        updateList(data)
        Picasso.get().load(data.icon).resize(32, 32).into(weather_icon)
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

    private fun getForecastWithUserCoordinates() {
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
                forecastViewModel.getForecastWithCoordinates(it.latitude, it.longitude)
            }
        }
    }

}
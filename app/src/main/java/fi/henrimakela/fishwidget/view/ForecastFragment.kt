package fi.henrimakela.fishwidget.view

import WeatherResponse
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import fi.henrimakela.domain.Status
import fi.henrimakela.fishwidget.R
import fi.henrimakela.fishwidget.viewmodel.ForecastViewModel
import kotlinx.android.synthetic.main.fragment_forecast.*
import kotlinx.android.synthetic.main.fragment_setup.*

class ForecastFragment : Fragment() {

    private lateinit var forecastViewModel: ForecastViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forecastViewModel =
            ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.main_nav_graph)).get(
                ForecastViewModel::class.java
            )
        getWeatherWithUserCoordinates()

        forecastViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            showLoading()
        })

        forecastViewModel.weatherResponse.observe(viewLifecycleOwner, Observer {

            when (it.status) {
                Status.SUCCESS -> updateViews(it.data!!)
                Status.ERROR -> showError(it.message!!)
            }

        })

        refresh.setOnClickListener {
            getWeatherWithUserCoordinates()
        }
    }

    private fun showLoading() {
        arrayOf(temperature, wind, error).forEach {
            it.visibility = View.GONE
        }
        progress_indicator.visibility = View.VISIBLE
    }

    private fun showError(message: String) {
        progress_indicator.visibility = View.GONE
        error.visibility = View.VISIBLE
        error.text = message
    }

    private fun updateViews(data: WeatherResponse) {
        error.visibility = View.GONE
        progress_indicator.visibility = View.GONE
        temperature.visibility = View.VISIBLE
        wind.visibility = View.VISIBLE
        temperature.text = "${data.current.temp} °C"
        wind.text = "${data.current.wind_speed} m/s"
    }

    private fun getWeatherWithUserCoordinates() {
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
                forecastViewModel.getWeatherWithCoordinates(it.latitude, it.longitude)
            }
        }
    }

}
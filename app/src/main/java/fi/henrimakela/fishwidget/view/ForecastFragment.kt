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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import fi.henrimakela.fishwidget.R
import fi.henrimakela.fishwidget.viewmodel.ForecastViewModel
import kotlinx.android.synthetic.main.fragment_forecast.*

class ForecastFragment : Fragment() {

    private lateinit var viewModel: ForecastViewModel
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
        viewModel = ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.main_nav_graph)).get(ForecastViewModel::class.java)
        getWeatherWithUserCoordinates()

        viewModel.weatherResponse.observe(viewLifecycleOwner, Observer {
            progress_indicator.visibility = View.GONE
            temperature.visibility = View.VISIBLE
            wind.visibility = View.VISIBLE
            temperature.text = "${it.current.temp} °C"
            wind.text = "${it.current.wind_speed} m/s"
        })
    }

    private fun getWeatherWithUserCoordinates(){
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
                viewModel.getWeatherWithCoordinates(it.latitude, it.longitude)
            }
        }
    }

}
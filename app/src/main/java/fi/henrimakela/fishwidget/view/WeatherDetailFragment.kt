/*
 * Fishwidget
 *
 * Copyright (c) 2020. Henri Mäkelä www.henrimakela.fi
 */

package fi.henrimakela.fishwidget.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import fi.henrimakela.domain.Status
import fi.henrimakela.fishwidget.R
import fi.henrimakela.fishwidget.viewmodel.ForecastViewModel
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.toolbar
import kotlinx.android.synthetic.main.fragment_weather_detail.*

class WeatherDetailFragment : Fragment(R.layout.fragment_weather_detail) {

    private lateinit var viewModel: ForecastViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupToolbar()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.main_nav_graph)).get(ForecastViewModel::class.java)

        viewModel.weatherResponse.observe(viewLifecycleOwner, Observer {
            if(it.status == Status.SUCCESS){
                temperature.text = it.data?.main?.temp.toString()
                wind.text = it.data?.wind?.speed.toString()
            }
        })
    }

    private fun setupToolbar() = toolbar.apply {
        setNavigationIcon(R.drawable.ic_back)
        title = resources.getString(R.string.settings)
        setNavigationOnClickListener { requireView().findNavController().popBackStack() }
    }
}
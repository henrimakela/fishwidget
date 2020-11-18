package fi.henrimakela.fishwidget.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import fi.henrimakela.fishwidget.R
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()

    }

    private fun setupToolbar() = toolbar.apply {
        setNavigationIcon(R.drawable.ic_back)
        title = resources.getString(R.string.settings)
        setNavigationOnClickListener { requireView().findNavController().popBackStack() }
    }
}
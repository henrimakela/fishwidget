package fi.henrimakela.fishwidget.widget

import android.Manifest
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.JobIntentService
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class GeoService : JobIntentService() {

    companion object {
        val UPDATE_ACTION: String = "com.henrimakela.fishwidget.ACTION_UPDATE_LOCATION"
        val GEOSERVICE_ERROR_KEY: String = "gse"
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onHandleWork(intent: Intent) {
        intent.action?.let {
            if (it == UPDATE_ACTION) {
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    fusedLocationClient.lastLocation.addOnSuccessListener {
                        sendLocationBroadcast(it.latitude, it.longitude)
                    }.addOnFailureListener {
                        sendErrorBroadcast(it.message)
                    }
                } else {
                    sendErrorBroadcast("No permissions")
                }
            }

        }
    }

    private fun sendLocationBroadcast(lat: Double?, lon: Double?) {
        val intent = Intent(this, FishWidgetProvider::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        intent.putExtra("lat", lat)
        intent.putExtra("lon", lon)

        val ids: IntArray = AppWidgetManager.getInstance(application).getAppWidgetIds(
            ComponentName(
                application, FishWidgetProvider::class.java
            )
        )
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        sendBroadcast(intent)
    }

    private fun sendErrorBroadcast(error: String?) {
        val intent = Intent(this, FishWidgetProvider::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        intent.putExtra(GEOSERVICE_ERROR_KEY, error)

        val ids: IntArray = AppWidgetManager.getInstance(application).getAppWidgetIds(
            ComponentName(
                application, FishWidgetProvider::class.java
            )
        )
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        sendBroadcast(intent)
    }
}

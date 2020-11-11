package fi.henrimakela.fishwidget.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.app.JobIntentService
import fi.henrimakela.domain.Status
import fi.henrimakela.fishwidget.MainActivity
import fi.henrimakela.fishwidget.R
import fi.henrimakela.usecases.GetFishForecast
import fi.henrimakela.usecases.GetWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class FishWidgetProvider : AppWidgetProvider(), KoinComponent {

    val getWeather: GetWeather by inject()
    val getFishForecast: GetFishForecast by inject()

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        startGeoServiceJob(context)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        val appWidgetManager = AppWidgetManager.getInstance(context)
        val views: RemoteViews = RemoteViews(
            context?.packageName,
            R.layout.fishwidget
        )

        intent?.let {
            if (it.action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
                when {
                    hasError(it) -> {
                        val error = it.getStringExtra(GeoService.GEOSERVICE_ERROR_KEY)
                        println("FishWidgetProvider: $error")
                    }
                    else -> {
                        if (hasCoordinates(it)) {
                            val lat = it.getDoubleExtra("lat", 0.0)
                            val lon = it.getDoubleExtra("lon", 0.0)
                            println("FishWidgetProvider: $lat $lon")

                            GlobalScope.launch(Dispatchers.Main) {
                                val weather = getWeather(lat, lon)
                                if (weather.status == Status.SUCCESS) {
                                    weather.data?.let {
                                        var forecast = getFishForecast(it)
                                        views.setTextViewText(R.id.title, forecast.temp.toString())
                                    }
                                }
                                context?.let {
                                    appWidgetManager.updateAppWidget(
                                        ComponentName(
                                            context,
                                            FishWidgetProvider::class.java
                                        ), views
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        appWidgetIds?.forEach { appWidgetId ->

            startGeoServiceJob(context)
            val pendingIntent: PendingIntent = Intent(context, MainActivity::class.java)
                .let { intent ->
                    PendingIntent.getActivity(context, 0, intent, 0)
                }
        }
    }

    private fun hasError(it: Intent): Boolean {
        return it.hasExtra(GeoService.GEOSERVICE_ERROR_KEY)
    }

    private fun hasCoordinates(it: Intent): Boolean {
        return it.hasExtra("lat") && it.hasExtra("lon")
    }

    private fun startGeoServiceJob(context: Context?) {
        val mIntent = Intent(context, GeoService::class.java)
        mIntent.action = GeoService.UPDATE_ACTION

        context?.let {
            JobIntentService.enqueueWork(context, GeoService::class.java, 123, mIntent)
        }
    }
}
/*
 * Fishwidget
 *
 * Copyright (c) 2020. Henri Mäkelä www.henrimakela.fi
 */

package fi.henrimakela.fishwidget

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.MutableLiveData
import fi.henrimakela.fishwidget.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FishWidgetApp : Application() {

    private var connection = MutableLiveData<Boolean>()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FishWidgetApp)
            modules(appModule)
        }
        monitorConnectivity()
    }

    private fun monitorConnectivity() {
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(connMonitor, intentFilter)
    }

    private val connMonitor = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            intent.extras?.let {
                if (intent.action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                    connection.value = !it.getBoolean(ConnectivityManager.EXTRA_NO_CONNECTIVITY)
                }
            }
        }

    }
}
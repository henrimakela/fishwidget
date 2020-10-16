package fi.henrimakela.fishwidget

import android.app.Application
import fi.henrimakela.fishwidget.di.appModule
import org.koin.core.context.startKoin

class FishWidgetApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }
    }
}
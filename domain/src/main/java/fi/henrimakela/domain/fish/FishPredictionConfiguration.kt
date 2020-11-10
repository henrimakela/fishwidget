package fi.henrimakela.domain.fish

class FishPredictionConfiguration private constructor(
    val wind: String,
    val noWind: String,
    val lowPressure: String,
    val steadyPressure: String,
    val visibilityClearSky: String,
    val visibilityCloudiness: String,
    val visibilityNightFall: String,
    val rainNormal: String,
    val rainHeavy: String,
    val waterTemp: String
) {

    class Builder {
        var wind: String = ""
        var noWind: String = ""
        var lowPressure: String = ""
        var steadyPressure: String = ""
        var visibilityClearSky: String = ""
        var visibilityCloudiness: String = ""
        var visibilityNightFall: String = ""
        var rainNormal: String = ""
        var rainHeavy: String = ""
        var waterTemp: String = ""

        fun wind(lambda: () -> String) {
            this.wind = lambda()
        }

        fun noWind(lambda: () -> String){
            this.noWind = lambda()
        }

        fun lowPressure(lambda: () -> String) {
            this.lowPressure = lambda()
        }

        fun steadyPressure(lambda: () -> String) {
            this.steadyPressure = lambda()
        }

        fun visibilityClearSky(lambda: () -> String) {
            this.visibilityClearSky = lambda()
        }

        fun visibilityCloudiness(lambda: () -> String) {
            this.visibilityCloudiness = lambda()
        }

        fun visibilityNightFall(lambda: () -> String) {
            this.visibilityNightFall = lambda()
        }

        fun rainNormal(lambda: () -> String) {
            this.rainNormal = lambda()
        }

        fun rainHeavy(lambda: () -> String) {
            this.rainHeavy = lambda()
        }

        fun waterTemp(lambda: () -> String) {
            this.waterTemp = lambda()
        }

        fun build() = FishPredictionConfiguration(
            wind,
            noWind,
            lowPressure,
            steadyPressure,
            visibilityClearSky,
            visibilityCloudiness,
            visibilityNightFall,
            rainNormal,
            rainHeavy,
            waterTemp
        )
    }
}

fun fishPredictionConfiguration(lambda: FishPredictionConfiguration.Builder.() -> Unit): FishPredictionConfiguration =
    FishPredictionConfiguration.Builder().apply(lambda).build()
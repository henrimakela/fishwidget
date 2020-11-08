package fi.henrimakela.data.repository

import WeatherResponse
import fi.henrimakela.domain.fish.FishForecast
import java.beans.Visibility

//http://www.kalassa.net/kalapedia/index.php/S%C3%A4%C3%A4n_vaikutus_kalastukseen
object FishPredictor {

    private val ICON_BASE_URL = "http://openweathermap.org/img/wn/"
    private val LOW_PRESSURE =
        "Ilmanpaineen nopeat ja suuret muutokset vaikuttavat kielteisesti kalojen aktiivisuuteen ja syöntihalukkuuteen. Näin käy etenkin silloin, kun ilmanpaine laskee voimakkaasti"
    private val STEADY_PRESSURE =
        "Hitaat, pienet ilmanpaineen muutokset tai muuttumaton ilmanpaine ovat kalastajan kannalta parempia, kuin äkillisesti muuttuva. Tasaisella ilmanpaineella kalat ovat ikäänkuin vilkkaita, ja käyvät hanakasti koukkuun."
    private val VISIBILITY_CLEAR_SKY =
        "Kun ilma on aurinkoinen ja taivaalla on vain vähän pilviä, kalat ovat varovaisia, arkoja ja valppaita. Tällöin kalastajan on oltava erityisen hiljainen ja varovainen."
    private val VISIBILITY_CLOUDINESS =
        "Pilvisyys vaikuttaa valaistukseen vedessä ja kalan kykyyn nähdä. Kalat eivät ole niin arkoja pilvisellä säällä kuin kirkkaalla."
    private val VISIBILITY_NIGHTFALL =
        "Iltahämärässä kalat usein jättävät vakituisen olinpaikkansa ja siirtyvät toiseen paikkaan. Petokalat siirtyvät silloin matalampaan veteen saalistamaan. Tällöin kalat ovat myös arkoja, joten kalasta varovaisesti."
    private val RAIN_NORMAL =
        "Kohtuullinen sade tekee usein kaloista vilkkaita, erityisesti silloin, kun sade viilentää lämmintä pintavettä. Sade hävittää myös varjoja, joten kalat eivät erota kalastajaa rannalta yhtä hyvin, kuin aurinkoisella säällä."
    private val RAIN_HEAVY =
        "Runsas ja voimakas sade hiljentää kalojen aktiivisuutta joksikin aikaa. Sade myös laskee veden lämpötilaa. Kalat ovat melko herkkiä lämpötilanvaiheluille, siksi sade sotkeekin kalojen ravinnonhakurytmin."
    private val WATER_TEMP =
        "Eri kalalajeilla on oma lämpötilansa, joissa ne viihtyvät parhaiten. Suotuisan lämpötilan sattuessa ne ovat aktiivisia, syövät enemmän ja kasvavat nopeammin sekä käyvät syöttiin hanakammin. Osa kaloista sietää niin kylmää, kuin lämmintäkin vettä. Tälaisia kaloja ovat ainakin ahven, hauki ja made."
    private val WIND =
        "tuulella kala näkee huonommin, kuin tyynellä, jolloin kalat ovat arkoja. Kalat ovat usein tuulenpuoleisella rannalla, joissa on enemmän ravintoa, ja happea."

    fun getPrediction(weather: WeatherResponse): FishForecast {
        var forecast = FishForecast(
            getFishWeatherDesc(weather.current.weather[0].id),
            getPressureDesc(weather.current.pressure),
            getWindDesc(weather.current.wind_speed),
            weather.current.weather[0].main,
            "$ICON_BASE_URL${weather.current.weather[0].icon}.png",
            weather.current.temp,
            weather.current.feels_like,
            weather.current.humidity,
            weather.current.pressure,
            weather.current.wind_speed,
            weather.current.wind_deg
        )
        return forecast
    }

    private fun getWindDesc(windSpeed: Double): String {
        if (windSpeed > 4) {
            return WIND
        }
        return "Tuulella ei ole vaikutusta kalastukseen tällä kelillä."
    }


    private fun getPressureDesc(pressure: Double): String {
        return when {
            pressure < 1013.25 -> {
                LOW_PRESSURE
            }
            else -> {
                STEADY_PRESSURE
            }
        }
    }

    private fun getFishWeatherDesc(id: Int): String {
        return when {
            id in 500..501 -> {
                RAIN_NORMAL
            }
            id in 502..531 -> {
                RAIN_HEAVY
            }
            id in 801..804 -> {
                VISIBILITY_CLOUDINESS
            }
            id == 800 -> {
                VISIBILITY_CLEAR_SKY
            }
            else -> {
                VISIBILITY_CLOUDINESS
            }
        }
    }

    private fun convertWindDegToString(deg: Double): String {
        return when {
            deg in 11.25..33.75 -> "NNE"
            deg in 33.75..56.25 -> "NE"
            deg in 56.25..78.75 -> "ENE"
            deg in 78.75..101.25 -> "E"
            deg in 101.25..123.75 -> "ESE"
            deg in 123.75..146.25 -> "SE"
            deg in 146.25..168.75 -> "SSE"
            deg in 168.75..191.25 -> "S"
            deg in 191.25..213.75 -> "SSW"
            deg in 213.75..236.25 -> "SW"
            deg in 236.25..258.75 -> "WSW"
            deg in 258.75..281.25 -> "W"
            deg in 281.25..303.75 -> "WNW"
            deg in 303.75..326.25 -> "NW"
            deg in 326.25..348.75 -> "NNW"
            else -> {
                "N"
            }
        }
    }

}

/*
*

Tuuli
Tavallisesti sanotaan, että kylmä pohjois- tai koillistuuli on huono kalatuuli. Lounaanpuoleista tuulta pidetään hyvänä kalatuulena. Erilaisista olosuhteista johtuen näissä säännöissä on kuitenkin paljon poikkeuksia. Myrskyllä ja kovalla tuulella kalaa on vaikea saada. Kalat ovat tällöin siirtyneet saarten ja niemien suojiin. Tällöin kannattaa kalastaa tuulensuojaisilta paikoilta. Tuulen tyynnyttyä kalat siirtyvät suojista normaaleille oleskelupaikoilleen. , joissa on yleensä tarjolla enemmän ravintoa. Kannattaa kokeilla onkia paikasta, joka on aallokon ja tyynen rajalla.

Normaali tuuli
Normaalilla tuulella kala näkee huonommin, kuin tyynellä, jolloin kalat ovat arkoja. Kalat ovat usein tuulenpuoleisella rannalla, joissa on enemmän ravintoa, ja happea.
* */
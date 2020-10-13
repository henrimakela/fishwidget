data class Current(
    val dt: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    val feels_like: Double,
    val pressure: Double,
    val humidity: Double,
    val dew_point: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Double,
    val wind_speed: Double,
    val wind_deg: Double,
    val weather: List<Weather>
)
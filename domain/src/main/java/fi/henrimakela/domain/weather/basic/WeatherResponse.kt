data class WeatherResponse (
	val coord : Coord,
	val weather : List<WeatherData>,
	val base : String,
	val main : Main,
	val visibility : Double,
	val wind : Wind,
	val clouds : Clouds,
	val dt : Double,
	val sys : Sys,
	val timezone : Int,
	val id : Int,
	val name : String,
	val cod : Int
)
package fi.henrimakela.fishwidget.util

import android.content.Context
import fi.henrimakela.fishwidget.R

private fun getWindDegString(deg: Double, context: Context): String {
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
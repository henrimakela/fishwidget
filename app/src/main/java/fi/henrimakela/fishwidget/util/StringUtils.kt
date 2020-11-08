package fi.henrimakela.fishwidget.util

import android.content.Context
import fi.henrimakela.fishwidget.R

private fun getWindDegString(deg: Double, context: Context): String {
    return when {
        deg in 11.25..33.75 -> context.getString(R.string.orientation_nne)
        deg in 33.75..56.25 -> context.getString(R.string.orientation_ne)
        deg in 56.25..78.75 -> context.getString(R.string.orientation_ene)
        deg in 78.75..101.25 -> context.getString(R.string.orientation_e)
        deg in 101.25..123.75 -> context.getString(R.string.orientation_ese)
        deg in 123.75..146.25 -> context.getString(R.string.orientation_se)
        deg in 146.25..168.75 -> context.getString(R.string.orientation_sse)
        deg in 168.75..191.25 -> context.getString(R.string.orientation_s)
        deg in 191.25..213.75 -> context.getString(R.string.orientation_ssw)
        deg in 213.75..236.25 -> context.getString(R.string.orientation_sw)
        deg in 236.25..258.75 -> context.getString(R.string.orientation_wsw)
        deg in 258.75..281.25 -> context.getString(R.string.orientation_w)
        deg in 281.25..303.75 -> context.getString(R.string.orientation_wnw)
        deg in 303.75..326.25 -> context.getString(R.string.orientation_nw)
        deg in 326.25..348.75 -> context.getString(R.string.orientation_nnw)
        else -> {
            context.getString(R.string.orientation_n)
        }
    }
}
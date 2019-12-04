package com.indramahkota.footballmatchschedule.utilities

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun formatDateFromString(inputDate: String): String? {
    val parsed: Date?
    var outputDate: String? = null
    val dfInput = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val dfOutput = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault())
    try {
        parsed = dfInput.parse(inputDate)
        if (parsed != null) {
            outputDate = dfOutput.format(parsed)
        }
    } catch (e: ParseException) {
        Log.d("Date Error", "ParseException - dateFormat")
    }
    return outputDate
}
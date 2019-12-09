package com.indramahkota.footballmatchschedule.utilities

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utilities {
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

    fun compareDateAfter(inputDate: String): Boolean {
        val parsed: Date?
        val dfInput = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        try {
            parsed = dfInput.parse(inputDate)
            return parsed.after(Date())
        } catch (e: ParseException) {
            Log.d("Date Error", "ParseException - dateFormat")
        }
        return false
    }

    fun compareDateBeforeAndEqual(inputDate: String): Boolean {
        val parsed: Date?
        val dfInput = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        try {
            parsed = dfInput.parse(inputDate)
            return (parsed.before(Date()) || parsed == Date())
        } catch (e: ParseException) {
            Log.d("Date Error", "ParseException - dateFormat")
        }
        return false
    }
}
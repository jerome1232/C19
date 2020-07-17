package com.example.c19.view

import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Axis formatter to be used by MPAndroidChart
 * @author Rodrigo Iturralde
 */
class MyXAxisValueFormatter(referenceDate: Date) : ValueFormatter() {

    val _referenceDate = referenceDate
    override fun getFormattedValue(value: Float): String {
        return SimpleDateFormat("yyyy-MM-dd").format(
            Date(
                _referenceDate.time + TimeUnit.DAYS.toMillis(value.toLong())
            )
        )
    }
}
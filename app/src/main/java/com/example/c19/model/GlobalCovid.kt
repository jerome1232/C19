package com.example.c19.model

import java.util.*

/**
 * Represents Global CVOID-19 Stats
 *
 * Note: all vals are immutable and are thus public
 * Data is fetched from covid19api.com
 *
 * @author Jeremy D. Jones
 * @property newConfirmed New confirmed cases today
 * @property totalConfirmed Total confirmed cases
 * @property newDeaths New deaths attributed to COVID-19 today
 * @property totalDeaths Total deaths attributed to COVID-19
 * @property newRecovered People that have recovered from COVID-19 Today
 * @property totalRecovered Total people that have recovered from COVID-19
 * @property fetchedDate Datetime that data was fetched, this is calculated and not from api
 */
data class GlobalCovid(
    val newConfirmed: Int,
    val totalConfirmed: Int,
    val newDeaths: Int,
    val totalDeaths: Int,
    val newRecovered: Int,
    val totalRecovered: Int
) {
    val fetchedDate: Calendar = Calendar.getInstance()
}

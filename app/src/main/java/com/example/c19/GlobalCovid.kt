package com.example.c19

/**
 * Represents Global CVOID-19 Stats
 *
 * @author Jeremy D. Jones
 * @property newConfirmed New confirmed cases today
 * @property totalConfirmed Total confirmed cases
 * @property newDeaths New deaths attributed to COVID-19 today
 * @property totalDeaths Total deaths attributed to COVID-19
 * @property newRecovered People that have recovered from COVID-19 Today
 * @property totalRecovered Total people that have recovered from COVID-19
 * @property fetchedDate Datetime that data was fetched
 */
data class GlobalCovid(
    val newConfirmed: Int,
    val totalConfirmed: Int,
    val newDeaths: Int,
    val totalDeaths: Int,
    val newRecovered: Int,
    val totalRecovered: Int
) {
    val fetchedDate = java.util.Calendar.getInstance()
}

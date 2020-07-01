package com.example.c19

/**
 * Represents a countries stats from day one to current
 *
 * @author Jeremy D. Jones
 *
 * @property country
 * @property countryCode
 * @property confirmed
 * @property deaths
 * @property date
 */
data class CountryCovidHistorical(
    val country: String,
    val countryCode: String,
    val confirmed: Int,
    val deaths: Int,
    val date: String
)

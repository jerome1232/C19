package com.example.c19.model

/**
 * Represents COVID-19 data for a single country
 *
 * @author Jeremy D. Jones
 *
 * @property country
 * @property countryCode
 * @property slug
 * @property date
 * @property newConfirmed
 * @property totalConfirmed
 * @property totalDeaths
 * @property totalRecovered
 */
data class CountryCovid(
    val country: String,
    val countryCode: String,
    val slug: String,
    val date: String,
    val newConfirmed: Int,
    val totalConfirmed: Int,
    val totalDeaths: Int,
    val totalRecovered: Int
)
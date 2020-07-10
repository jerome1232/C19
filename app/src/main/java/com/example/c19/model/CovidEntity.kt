package com.example.c19.model

import com.google.gson.annotations.SerializedName

/**
 * This class represents a generic
 * covid 19 data entity
 *
 * @author Jeremy D. Jones
 *
 */
abstract class CovidEntity {
    abstract val newConfirmed: Int
    abstract val totalConfirmed: Int
    abstract val newDeaths: Int
    abstract val totalDeaths: Int
}

/**
 * Represents Covid 19 data for a country
 *
 * @author Jeremy D. Jones
 * @property country
 * @property date
 * @property newConfirmed
 * @property totalConfirmed
 * @property newDeaths
 * @property totalDeaths
 * @property newRecovered
 * @property totalRecovered
 */
data class CountryCovid(
    val country: String,
    @SerializedName("updated")
    val date: String,
    @SerializedName("todayCases")
    override val newConfirmed: Int,
    @SerializedName("cases")
    override val totalConfirmed: Int,
    @SerializedName("todayDeaths")
    override val newDeaths: Int,
    @SerializedName("deaths")
    override val totalDeaths: Int,
    @SerializedName("todayRecovered")
    val newRecovered: Int,
    @SerializedName("recovered")
    val totalRecovered: Int
    ) : CovidEntity()

/**
 * Represents Covid 19 data for a state
 *
 * @author Jeremy D. Jones
 * @property state
 * @property newConfirmed
 * @property totalConfirmed
 * @property newDeaths
 * @property totalDeaths
 * @property date
 */
data class StateUsCovid(
    val state: String,
    @SerializedName("todayCases")
    override val newConfirmed: Int,
    @SerializedName("cases")
    override val totalConfirmed: Int,
    @SerializedName("todayDeaths")
    override val newDeaths: Int,
    @SerializedName("deaths")
    override val totalDeaths: Int,
    @SerializedName("updated")
    val date: Long
    ) : CovidEntity()

/**
 * Represents Global Covid 19 Stats
 *
 * @author Jeremy D. Jones
 * @property newConfirmed New confirmed cases today
 * @property totalConfirmed Total confirmed cases
 * @property newDeaths New deaths attributed to COVID-19 today
 * @property totalDeaths Total deaths attributed to COVID-19
 * @property newRecovered People that have recovered from COVID-19 Today
 * @property totalRecovered Total people that have recovered from COVID-19
 */
data class GlobalCovid(
    @SerializedName("NewConfirmed")
    override val newConfirmed: Int,
    @SerializedName("TotalConfirmed")
    override val totalConfirmed: Int,
    @SerializedName("NewDeaths")
    override val newDeaths: Int,
    @SerializedName("TotalDeaths")
    override val totalDeaths: Int,
    @SerializedName("NewRecovered")
    val newRecovered: Int,
    @SerializedName("TotalRecovered")
    val totalRecovered: Int
) : CovidEntity()

/**
 * Simple data class to hold Global Covid 19 data
 *
 * @author Jeremy Jones
 *
 * @property global
 * @property data
 */
data class GlobalCovidSummary (
    // these aren't really used...
    // they are just here to satisfy inheritance
    override val newConfirmed: Int,
    override val totalConfirmed: Int,
    override val newDeaths: Int,
    override val totalDeaths: Int,
    // The real stuff
    @SerializedName("Global")
    val global: GlobalCovid,
    @SerializedName("Date")
    val data: String
) : CovidEntity()

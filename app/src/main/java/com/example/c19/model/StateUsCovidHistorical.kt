package com.example.c19.model

import com.google.gson.annotations.SerializedName

/**
 * Represents all daily data from day one to current
 *
 * @author Jeremy D. Jones
 *
 * @property date
 * @property state
 * @property confirmed
 * @property recovered
 * @property deaths
 */
data class StateUsCovidHistorical(
    val date: String,
    val state: String,
    @SerializedName("cases")
    val confirmed: Int,
    @SerializedName("deaths")
    val deaths: Int
)
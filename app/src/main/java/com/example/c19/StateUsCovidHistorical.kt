package com.example.c19

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
    val date: Int,
    val state: String,
    @SerializedName("positive")
    val confirmed: Int,
    val recovered: Int,
    @SerializedName("death")
    val deaths: Int
)
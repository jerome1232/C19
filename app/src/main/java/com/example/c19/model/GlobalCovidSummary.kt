package com.example.c19.model

import com.google.gson.annotations.SerializedName

/**
 * @author Jeremy Jones
 *
 * @property global
 * @property data
 */
data class GlobalCovidSummary (
    @SerializedName("Global")
    val global: GlobalCovid,
    @SerializedName("Date")
    val data: String
)
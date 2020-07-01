package com.example.c19.model

import com.google.gson.annotations.SerializedName

data class GlobalCovidSummary (
    @SerializedName("Global")
    val global: GlobalCovid,
    @SerializedName("Date")
    val data: String
)
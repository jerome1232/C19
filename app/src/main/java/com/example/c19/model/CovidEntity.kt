package com.example.c19.model

import com.google.gson.annotations.SerializedName

sealed class CovidEntity {
    data class CountryCovid(

        val country: String,
        @SerializedName("updated")
        val date: String,
        @SerializedName("todayCases")
        val newConfirmed: Int,
        @SerializedName("cases")
        val totalConfirmed: Int,
        @SerializedName("todayDeaths")
        val newDeaths: Int,
        @SerializedName("deaths")
        val totalDeaths: Int,
        @SerializedName("todayRecovered")
        val newRecovered: Int,
        @SerializedName("recovered")
        val totalRecovered: Int
    ) : CovidEntity()

    data class StateUsCovid(
        val state: String,
        @SerializedName("todayCases")
        val newConfirmed: Int,
        @SerializedName("cases")
        val totalConfirmed: Int,
        @SerializedName("todayDeaths")
        val newDeaths: Int,
        @SerializedName("deaths")
        val totalDeaths: Int,
        val updated: Long
    ) : CovidEntity()
}
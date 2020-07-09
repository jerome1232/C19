package com.example.c19.model

import com.google.gson.annotations.SerializedName

abstract class CovidEntity {
    abstract val newConfirmed: Int
    abstract val totalConfirmed: Int
    abstract val newDeaths: Int
    abstract val totalDeaths: Int
}

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

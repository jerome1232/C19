package com.example.c19.model

import com.google.gson.annotations.SerializedName
import java.util.*


abstract class CovidHistEntity {
    abstract val name: String
    abstract val confirmed: Int
    abstract val deaths: Int
    abstract val date: String
}

// val name : String
// val confirmed : Int
// val deaths : Int
// val recovered : Int
// val active : Int
// val date : String

// https://corona.lmao.ninja/v2/nyt/states?state
data class CovidHistState(
    @SerializedName("state")
    override val name: String,
    @SerializedName("cases")
    override val confirmed: Int,
    override val deaths: Int,
    override val date: String
) : CovidHistEntity()

// https://api.covid19api.com/total/dayone/country/:country
data class CovidHistCountry(
    @SerializedName("Country")
    override val name: String,
    @SerializedName("Confirmed")
    override val confirmed: Int,
    @SerializedName("Deaths")
    override val deaths: Int,
    @SerializedName("Date")
    override val date: String
) : CovidHistEntity()
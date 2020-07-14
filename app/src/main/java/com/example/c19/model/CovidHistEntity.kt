package com.example.c19.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * A generic class to represent
 * historical covid 19 data
 *
 * @author Jeremy D. Jones
 *
 */
abstract class CovidHistEntity {
    abstract val name: String
    abstract val confirmed: Int
    abstract val deaths: Int
    abstract val date: String
}

/**
 * Represents Historical data for US States
 *
 * @author Jeremy D. Jones
 *
 * The api url: https://corona.lmao.ninja/v2/nyt/states?state
 *      Unfortunately there is no way to specify a single state
 *      so the state desired is sorted out as the data is retrieved.
 *
 * @property name
 * @property confirmed
 * @property deaths
 * @property date
 */
data class CovidHistState(
    @SerializedName("state")
    override val name: String,
    @SerializedName("cases")
    override val confirmed: Int,
    override val deaths: Int,
    override val date: String
) : CovidHistEntity()

/**
 * Represents Historical data of Countries
 *
 * @author Jeremy D. Jones
 *
 * The api url: https://api.covid19api.com/total/dayone/country/:country
 *      where :country is the country desired
 * @property name
 * @property confirmed
 * @property deaths
 * @property date
 */
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
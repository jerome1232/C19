package com.example.c19.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type

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
 * @property name
 * @property date
 * @property newConfirmed
 * @property totalConfirmed
 * @property newDeaths
 * @property totalDeaths
 * @property newRecovered
 * @property totalRecovered
 */
data class CountryCovid(
    @SerializedName("country")
    val name: String,
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
    val totalRecovered: Int,
    val countryInfo: CountryInfo
    ) : CovidEntity()

data class CountryInfo(
    val flag: String
)

/**
 * Represents Covid 19 data for a state
 *
 * @author Jeremy D. Jones
 * @property name
 * @property newConfirmed
 * @property totalConfirmed
 * @property newDeaths
 * @property totalDeaths
 * @property date
 */
data class StateUsCovid(
    @SerializedName("state")
    val name: String,
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
    val totalRecovered: Int,
    @SerializedName("Date")
    val date: String
): CovidEntity()

/**
 * Simple data class to hold Global Covid 19 data
 *
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

/**
 * Custom builds a GlobalCovid object to more closely
 * resemble other data types.
 *
 * @author Jeremy D. Jones
 *
 */
class GlobalDeserializer: JsonDeserializer<GlobalCovid> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): GlobalCovid {
        val jsonObject = json.asJsonObject

        /*
        I know, I used single letter variables here
        but their purpose should be clear based on the
        key they are being retrieved from.
        */
        val d = jsonObject.get("Date").asString
        val g = jsonObject.get("Global").asJsonObject
        val nc = g.get("NewConfirmed").asInt
        val tc = g.get("TotalConfirmed").asInt
        val nd = g.get("NewDeaths").asInt
        val td = g.get("TotalDeaths").asInt
        val nr = g.get("NewRecovered").asInt
        val tr = g.get("TotalRecovered").asInt

        return GlobalCovid(nc, tc, nd, td, nr, tr, d)
    }
}
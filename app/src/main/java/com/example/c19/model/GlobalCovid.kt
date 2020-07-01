package com.example.c19.model

import android.provider.Settings
import android.util.Log
import com.google.gson.*
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import java.lang.reflect.Type
import java.util.*

/**
 * Represents Global CVOID-19 Stats
 *
 * Note: all vals are immutable and are thus public
 * Data is fetched from covid19api.com
 *
 * @author Jeremy D. Jones
 * @property newConfirmed New confirmed cases today
 * @property totalConfirmed Total confirmed cases
 * @property newDeaths New deaths attributed to COVID-19 today
 * @property totalDeaths Total deaths attributed to COVID-19
 * @property newRecovered People that have recovered from COVID-19 Today
 * @property totalRecovered Total people that have recovered from COVID-19
 * @property fetchedDate Datetime that data was fetched, this is calculated and not from api
 */
data class GlobalCovid(
    @SerializedName("NewConfirmed")
    val newConfirmed: Int,
    @SerializedName("TotalConfirmed")
    val totalConfirmed: Int,
    @SerializedName("NewDeaths")
    val newDeaths: Int,
    @SerializedName("TotalDeaths")
    val totalDeaths: Int,
    @SerializedName("NewRecovered")
    val newRecovered: Int,
    @SerializedName("TotalRecovered")
    val totalRecovered: Int
)

class GlobalDeserializer : JsonDeserializer<GlobalCovid> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): GlobalCovid {
        val content = json?.asJsonObject?.get("Global")?.asJsonObject
        Log.i("deserial", content.toString())
        return Gson().fromJson(content, GlobalCovid::class.java)
    }
}


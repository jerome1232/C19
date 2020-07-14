package com.example.c19.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Jeremy D. Jones
 *
 * This is an interface to fetch data for Covid-19 states
 * from various API's
 *
 */
interface CovidApi{

    // For current state data
    @GET("states/{state}")
    fun getState(@Path("state") key: String): Call<StateUsCovid>

    // For current country data
    @GET("countries/{country}?yesterday=true&strict=true")
    fun getCountry(@Path("country") key: String): Call<CountryCovid>

    // For global summary data
    @GET("summary")
    fun getGlobal(): Call<GlobalCovid>

    // For historical Country data
    @GET("total/dayone/country/{country}")
    fun getHistCountry(@Path("country") key: String): Call<List<CovidHistCountry>>

    // For state historical data
    @GET("nyt/states?state")
    fun getHistState(): Call<List<CovidHistState>>
}
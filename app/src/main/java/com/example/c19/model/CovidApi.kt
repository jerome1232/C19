package com.example.c19.model

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Jeremy D. Jones
 *
 * This is an interface to fetch data for state API
 *
 */
interface CovidApi{
/*
This adds the parameters on to the baseURL. In this case requesting a specific state.
It returns a Call, this is something the retrofit library will convert to the data class
later.
*/
    @GET("states/{state}")
    fun getState(@Path("state") key: String): Call<StateUsCovid>

    @GET("countries/{country}?yesterday=true&strict=true")
    fun getCountry(@Path("country") key: String): Call<CountryCovid>

    @GET("summary")
    fun getGlobal(): Call<GlobalCovid>

    @GET("total/dayone/country/{country}")
    fun getHistCountry(@Path("country") key: String): Call<CovidHistCountry>

    @GET("nyt/states?state")
    fun getHistState(): Call<CovidHistState>
}
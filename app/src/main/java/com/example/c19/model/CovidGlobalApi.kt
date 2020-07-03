package com.example.c19.model

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Interface to fetch global Covid 19 data from api
 *
 */
interface CovidGlobalApi {
    @GET("summary")
    fun getGlobal(): Call<GlobalCovidSummary>


    companion object {
        fun create(): CovidGlobalApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.covid19api.com/")
                .build()
            return  retrofit.create(CovidGlobalApi::class.java)
        }
    }
}
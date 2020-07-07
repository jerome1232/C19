package com.example.c19.model

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Interface to fetch historical data for US states
 *
 * @author Jeremy D. Jones
 *
 */
interface CovidHistStateApi {
    @GET("nyt/states?state")
    fun getHistState(): Call<StateUsCovidHistorical>

    companion object {
        fun create(): CovidHistStateApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://corona.lmao.ninja/")
                .build()
            return retrofit.create(CovidHistStateApi::class.java)
        }
    }
}
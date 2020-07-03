package com.example.c19.model

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface CovidCountryApi {

    @GET("countries/{country}?yesterday=true&strict=true")
    fun getCountry(@Path("country") key: String): Call<CountryCovid>

    companion object {
        fun create(): CovidCountryApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://corona.lmao.ninja/v2/")
                .build()
            return retrofit.create(CovidCountryApi::class.java)
        }
    }
}
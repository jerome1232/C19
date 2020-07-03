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
interface CovidStateApi {
/*
This adds the parameters on to the baseURL. In this case requesting a specific state.
It returns a Call, this is something the retrofit library will convert to the data class
later.
*/
    @GET("states/{state}")
    fun getState(@Path("state") key: String): Call<StateUsCovid>

/*
Here is where all the conversion happens.
*/
    companion object {
        fun create(): CovidStateApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://corona.lmao.ninja/v2/")
                .build()

            return retrofit.create(CovidStateApi::class.java)
        }
    }
}
package com.example.c19

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Jeremy D. Jones
 *
 * This is an interface to facilitate reaching out to
 * the various API's.
 *
 */
interface NovelCovidApi {
    // This adds the parameters on to the baseURL. In this case requesting a specific state.
    // It returns a Call, this is something the retrofit library will convert to the data class
    // later.
    @GET("states/{state}")
    fun getState(@Path("state") key: String): Call<StateUsCovid>

/*
Here is where all the conversion happens.
Note RxJava is doing work in the background
*/
    companion object {
        fun create(): NovelCovidApi {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://corona.lmao.ninja/v2/states/")
                .build()

            return retrofit.create(NovelCovidApi::class.java)
        }
    }
}
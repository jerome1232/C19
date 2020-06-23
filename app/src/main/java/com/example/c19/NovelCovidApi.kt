package com.example.c19

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

//private val url = "https://corona.lmao.ninja/v2/"
//https://corona.lmao.ninja/v2/states/:states?yesterday=true


/**
 * @author Jeremy D. Jones
 *
 * This is an interface to facilitate reaching out to
 * the various API's.
 *
 */
interface NovelCovidApi {
    @GET("states/{state}")
    fun getState(@Path("state") key: String): Call<StateUsCovid>

    companion object {
        fun create(): NovelCovidApi {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        }
    }
}
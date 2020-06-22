package com.example.c19

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

//private val url = "https://corona.lmao.ninja/v2/"
//https://corona.lmao.ninja/v2/states/:states?yesterday=true
interface NovelCovidApi {
    @GET("states/{state}")
    fun getState(@Path("state") key: String): Call<StateUsCovid>
}
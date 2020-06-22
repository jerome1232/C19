package com.example.c19

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private val url = "https://corona.lmao.ninja/v2/"
interface NovelCovidApi {
    @GET("states?sort&yesterday")
    Call<List<StateUsCovid>> getStates()
}
package com.example.c19

import android.util.Log
import com.google.gson.Gson

class CovidManager {
    private val TAG = "CovidManager"
    private val url = "https://corona.lmao.ninja/v2/states?sort&yesterday"
    private val gson = Gson()
    var states = mutableListOf<StateUsCovid>()

    fun fetchApi() {
        Log.i(TAG, url)
    }
}
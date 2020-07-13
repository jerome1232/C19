package com.example.c19.model

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale.ROOT

class CovidHistManager {
    private val TAG = "CovidHistManager"

    companion object {
        private var states = mutableListOf<List<CovidHistState>>()
        private var countries = mutableListOf<List<CovidHistCountry>>()
    }

    fun getHistEntity(name: String) : List<CovidHistEntity> {
        var entity : List<CovidHistEntity> = getState(name)
        if (entity.isNotEmpty()) return entity
        entity = getCountry(name)
        return entity
    }

    private fun getState(name: String) : List<CovidHistState> {
        var state = searchState(name)
        if (state.isNotEmpty()) return state
        state = apiStateFetch(name)
        return state
    }

    private fun apiStateFetch(name: String): List<CovidHistState> {

        return emptyList()
    }

    private fun searchState(name: String) : List<CovidHistState> {
        Log.i(TAG, "Searching for $name")
        for (item in states) {
            if (item[0].name.toLowerCase(ROOT) == name.toLowerCase(ROOT)) {
                Log.i(TAG, "$name found as state")
                Log.i(TAG, item[0].toString())
                return item
            }
        }
        Log.i(TAG, "Not found as state")
        return emptyList()
    }

    private fun getCountry(name: String) : List<CovidHistCountry> {
        var country = searchCountry(name)
        if (country.isNotEmpty()) return country
        country = apiCountryFetch(name)
        return country
    }

    private fun searchCountry(name: String): List<CovidHistCountry> {
        Log.i(TAG, "Searching for $name")
        for (item in countries) {
            if (item[0].name.toLowerCase(ROOT) == name.toLowerCase(ROOT)) {
                Log.i(TAG, "$name found")
                Log.i(TAG, item[0].toString())
                return item
            }
        }
        return emptyList()

    }

    private fun apiCountryFetch(name: String): List<CovidHistCountry> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.covid19api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(CovidApi::class.java)
        val call = service.getHistCountry(name)
        val response = call.execute()
        Log.i(TAG, "Country response: ${response.code()}")
        if (response.code() == 200) return response.body() as List<CovidHistCountry>
        return emptyList()
    }
}
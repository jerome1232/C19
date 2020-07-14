package com.example.c19.model

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale.ROOT

/**
 * Manages historical data for states and countries
 *
 * @author Jeremy D. Jones
 *
 */
class CovidHistManager {
    private val TAG = "CovidHistManager"

    /**
     * These contain lists of lists, each country/state
     * comes in as a list of dates/cases
     */
    companion object {
        private var states = mutableListOf<List<CovidHistState>>()
        private var countries = mutableListOf<List<CovidHistCountry>>()
    }

    /**
     * Retrieves historical data for a state or country
     *
     * @author Jeremy D. Jones
     * @param name
     * @return
     */
    fun getHistEntity(name: String) : List<CovidHistEntity> {
        var entity : List<CovidHistEntity> = getState(name)
        if (entity.isNotEmpty()) {

            return entity
        }
        entity = getCountry(name)
        return entity
    }

    /**
     * Retrieves historical data for a state
     *
     * @author Jeremy D. Jones
     * @param name
     * @return
     */
    private fun getState(name: String) : List<CovidHistState> {
        var state = searchState(name)
        if (state.isNotEmpty()) return state
        state = apiStateFetch()
        val stateWanted = mutableListOf<CovidHistState>()
        for (item in state) {
            if (item.name.toLowerCase(ROOT) == name.toLowerCase(ROOT)) {
                stateWanted.add(item)
            }
        }
        return stateWanted
    }

    /**
     * Retrieves historical data for a state from API
     *
     * @author Jeremy D. Jones
     * @return
     */
    private fun apiStateFetch(): List<CovidHistState> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://corona.lmao.ninja/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(CovidApi::class.java)
        val call = service.getHistState()
        val response = call.execute()
        if (response.code() == 200) {
            return response.body() as List<CovidHistState>
        }
        return emptyList()
    }

    /**
     * Searches memory for a given state
     *
     * @author Jeremy D. Jones
     * @param name
     * @return
     */
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

    /**
     * Retrieves historical data for a country
     *
     * @author Jeremy D. Jones
     * @param name
     * @return
     */
    private fun getCountry(name: String) : List<CovidHistCountry> {
        var country = searchCountry(name)
        if (country.isNotEmpty()) return country
        country = apiCountryFetch(name)
        return country
    }

    /**
     * Searches memory for a given country
     *
     * @author Jeremy D. Jones
     * @param name
     * @return
     */
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

    /**
     * Retrieves historical data for a country from API
     *
     * @author Jeremy D. Jones
     * @param name
     * @return
     */
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
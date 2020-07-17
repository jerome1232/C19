package com.example.c19.model

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale.ROOT

private const val TAG = "CovidHistManager"

/**
 * Manages historical data for states and countries
 *
 * @author Jeremy D. Jones
 *
 */
class CovidHistManager {
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
        // Checking state list to see if we already have this item in memory
        var state = searchState(name)
        if (state.isNotEmpty()) return state

        // If we don't have the state, get it from API
        state = apiStateFetch()

        // Unfortunately this api doesn't allow specification of a single state.
        // So I loop through data returned and only grab the state of interest.
        val stateWanted = mutableListOf<CovidHistState>()
        for (item in state) {
            if (item.name.toLowerCase(ROOT) == name.toLowerCase(ROOT)) {
                stateWanted.add(item)
            }
        }
        // Return a list of historical data, on failure it returns an empty list
        if (stateWanted.isNotEmpty()) Log.i(TAG, "\"$name\" retrieved from api")
        else Log.i(TAG, "\"$name\" not found in state API")
        return stateWanted
    }

    /**
     * Retrieves historical data for a state from API
     *
     * @author Jeremy D. Jones
     * @return
     */
    private fun apiStateFetch(): List<CovidHistState> {
        Log.i(TAG, "Retrieving from state API")
        // retrofit 2
        val retrofit = Retrofit.Builder()
            .baseUrl("https://corona.lmao.ninja/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(CovidApi::class.java)
        val call = service.getHistState()
        val response = call.execute()

        // If response code is good, return the data
        if (response.code() == 200) return response.body() as List<CovidHistState>
        // on failure, return an empty list
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
            if (item.first().name.toLowerCase(ROOT) == name.toLowerCase(ROOT)) {
                Log.i(TAG, "$name found as state")
                return item
            }
        }
        Log.i(TAG, "Not found as state")
        // On failure to find a state, return an empty list
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
        // First search memory to see if we already retrieved this country
        var country = searchCountry(name)
        if (country.isNotEmpty()) return country

        // Otherwise retrieve it from API
        country = apiCountryFetch(name)
        if (country.isNotEmpty()) Log.i(TAG, "\"$name\" retrieved from API")

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
            if (item.first().name.toLowerCase(ROOT) == name.toLowerCase(ROOT)) {
                Log.i(TAG, "$name found")
                return item
            }
        }
        // On failure return an empty list
        Log.i(TAG, "\"$name\" not found in memory")
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
        Log.i(TAG, "Retrieving \"$name\" from country api")
        // Retrofit 2 portion
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.covid19api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(CovidApi::class.java)
        val call = service.getHistCountry(name)
        val response = call.execute()

        // On a good response, return the data
        if (response.code() == 200) return response.body() as List<CovidHistCountry>
        // otherwise return an empty list
        return emptyList()
    }
}
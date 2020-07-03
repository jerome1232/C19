package com.example.c19.model

import android.util.Log
import java.util.Locale.ROOT

class CovidCountryManager {
    private val TAG = "CovidCountryManager"

    companion object {
        var countries = mutableListOf<CountryCovid>()
    }

    /**
     * Retrieves a country
     *
     * @author Jeremy D. Jones
     *
     * @param country
     * @return
     */
    fun getCountry(country: String): CountryCovid? {
        /**
         * TODO If list is empty load from disk
         */
        if (countries.isEmpty()) {
            loadFromDisk()
        }

        var countryCovid = searchList(country)
        if (null != countryCovid) return countryCovid
        countryCovid = apiCountryFetch(country)
        if (countryCovid != null) {
            countries.add(countryCovid)
            return countryCovid
        }
        return null
    }

    /**
     * Fetches a country from API
     *
     * @author Jeremy D. Jones
     *
     * @param country
     * @return
     */
    private fun apiCountryFetch(country: String): CountryCovid? {
        Log.i(TAG, "Making request to get $country")
        val service = CovidCountryApi.create()
        val call = service.getCountry(country)
        Log.i(TAG, call.toString())
        val response = call.execute()
        if (response.code() == 200) return response.body()
        return null
    }

    /**
     * Searches memory for a country
     *
     * @author Jeremy D. Jones
     *
     * @param country
     * @return
     */
    private fun searchList(country: String): CountryCovid? {
        Log.i(TAG, "Searching for $country")
        for (item in countries) {
            if (item.country.toLowerCase(ROOT) == country.toLowerCase(ROOT)) {
                Log.i(TAG, "$country found")
                Log.i(TAG, item.toString())
                /**
                 * TODO: Check timestamp to see if data is super fresh
                 */
                return item
            }
        }
        return null
    }

    /**
     * TODO Implement loadFromDisk function
     *
     */
    private fun loadFromDisk() {
        // stub
    }
}
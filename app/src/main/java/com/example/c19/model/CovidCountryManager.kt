package com.example.c19.model

import android.util.Log

class CovidCountryManager {
    private val TAG = "CovidCountryManager"

    companion object {
        var countries = mutableListOf<CountryCovid>()
    }


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

    private fun apiCountryFetch(country: String): CountryCovid? {
        Log.i(TAG, "Making request to get $country")
        val service = CovidCountryApi.create()
        val call = service.getCountry(country)
        Log.i(TAG, call.toString())
        val response = call.execute()
        if (response.code() == 200) return response.body()
        return null
    }

    private fun searchList(country: String): CountryCovid? {
        TODO("Not yet implemented")
    }

    private fun loadFromDisk() {
        TODO("Not yet implemented")
    }
}
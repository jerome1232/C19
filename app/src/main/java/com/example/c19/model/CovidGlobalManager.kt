package com.example.c19.model

import android.util.Log

/**
 * A manager to control Global Covid 19 data
 *
 * @author Jeremy Jones
 *
 */
class CovidGlobalManager {
    private val TAG = "CovidGlobalManager"


    fun getGlobal(): GlobalCovidSummary? {
        return apiFetch()
    }

    private fun loadFromDisk() {
        // stub
    }

    private fun apiFetch(): GlobalCovidSummary? {
        val service = CovidGlobalApi.create()
        val call = service.getGlobal()
        val response = call.execute()
        Log.i(TAG, response.code().toString())
        Log.i(TAG, response.body().toString())
        if (response.code() == 200) return response.body()
        return null
    }


}

package com.example.c19.model

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale.ROOT
import kotlin.jvm.java as java1

/**
 *  CovidManager manages current data for
 *  states, countries, and global
 *
 *  @author Jeremy D. Jones
 *
 * TODO: Add disk load if list is empty
 * TODO: Check timestamp to see if we need to attempt to refresh data
 *
 */
class CovidManager {
    private val TAG = "CovidManager"

    // keeping independent lists for states and countries.
    companion object {
        private var states = mutableListOf<StateUsCovid>()
        private var countries = mutableListOf<CountryCovid>()
    }

    /**
     * Retrieves global summary stats
     *
     * @author Jeremy D. Jones
     *
     * @return
     */
    fun getGlobal() : CovidEntity? {
        return apiGlobalFetch()
    }

    /**
     * Retrieves either a state or a country
     *
     * @author Jeremy D. Jones
     * @param entityName
     * @return
     */
    fun getEntity(entityName: String): CovidEntity? {
        // first try fetching a state
        var entity : CovidEntity? = getState(entityName)
        if (entity != null) return entity
        // if fetching a state failed maybe it's a country
        entity = getCountry(entityName)
        if (entity != null) return entity
        // this place doesn't exist in our api's
        return null
    }

    /**
     * Retrieves a country
     *
     * @author Jeremy D. Jones
     * @param country
     * @return
     */
    private fun getCountry(country: String): CountryCovid? {
        /**
         * TODO If list is empty load from disk
         */
        if (countries.isEmpty()) {
            loadFromDisk()
        }

        // first search list for a country
        var countryCovid = searchCountryList(country)
        if (null != countryCovid) return countryCovid
        // if no match was found, fetch from the api
        countryCovid = apiCountryFetch(country)
        if (countryCovid != null) {
            countries.add(countryCovid)
            return countryCovid
        }
        // return null on failure to find a country
        return null
    }

    /**
     * Retrieves a state
     *
     * @author Jeremy D. Jones
     * @param state
     * @return returns null on failure, a StateUsCovid object on success
     */
    private fun getState(state: String): StateUsCovid? {
        /**
         * TODO: If list is empty load from disk
         */
        if (states.isEmpty()) {
            loadFromDisk()
        }
        // first search the list for a state
        var stateUsCovid = searchStateList(state)
        if (stateUsCovid != null) return stateUsCovid
        // if no match was found, fetch data from the api
        stateUsCovid = apiStateFetch(state)
        if (stateUsCovid != null) {
            states.add(stateUsCovid)
            return stateUsCovid
        }
        // return null on failure to find a state
        return null
    }

    private fun apiGlobalFetch(): GlobalCovid? {
        val customGson = GsonBuilder()
            .registerTypeAdapter(GlobalCovid::class.java1, GlobalDeserializer())
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.covid19api.com/")
            .addConverterFactory(GsonConverterFactory.create(customGson))
            .build()
        val service = retrofit.create(CovidApi::class.java1)
        val call = service.getGlobal()
        val response = call.execute()
        Log.i(TAG, "Response: ${response.code().toString()}")
        Log.i(TAG, response.body().toString())
        if (response.code() == 200) return response.body()
        return null
    }
    /**
     * TODO Implement this
     *
     */
    private fun loadFromDisk() {
        // stub
    }

    /**
     * apiStateFetch fetches data from the api
     *
     * @author Jeremy D. Jones
     * @param state
     */
    private fun apiStateFetch(state: String ): StateUsCovid? {
        Log.i(TAG, "Making request to get $state")
        val retrofit = Retrofit.Builder()
            .baseUrl("https://corona.lmao.ninja/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(CovidApi::class.java1)
        val call = service.getState(state)
        Log.i(TAG, call.toString())
        val response = call.execute()
        if (response.code() == 200) return response.body()
        return null
    }

    /**
     * searches list for a given state
     *
     * @author Jeremy D. Jones
     * @param state
     * @return
     */
    private fun searchStateList(state: String ): StateUsCovid? {
        Log.i(TAG, "Searching for $state")
        for (item in states) {
            if (item.name.toLowerCase(ROOT) == state.toLowerCase(ROOT)) {
                Log.i(TAG, "$state found")
                Log.i(TAG, item.toString())
                /**
                 * TODO: Check timestamp to see if data is super fresh
                 * I'll want to attempt to get fresh data, and if we can't get fresh data
                 * resort to using the old data.
                 */
                return item
            }
        }
    return null
    }

    /**
     * Fetches a country from API
     *
     * @author Jeremy D. Jones
     * @param country
     * @return
     */
    private fun apiCountryFetch(country: String): CountryCovid? {
        Log.i(TAG, "Making request to get $country")
        val retrofit = Retrofit.Builder()
            .baseUrl("https://corona.lmao.ninja/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(CovidApi::class.java1)
        val call = service.getCountry(country)
        Log.i(TAG, call.toString())
        val response = call.execute()
        Log.i(TAG, "Response code: ${response.code()}")
        if (response.code() == 200) return response.body()
        return null
    }

    /**
     * Searches memory for a country
     *
     * @author Jeremy D. Jones
     * @param country
     * @return
     */
    private fun searchCountryList(country: String): CountryCovid? {
        Log.i(TAG, "Searching for $country")
        for (item in countries) {
            if (item.name.toLowerCase(ROOT) == country.toLowerCase(ROOT)) {
                Log.i(TAG, "$country found")
                Log.i(TAG, item.toString())
                /**
                 * TODO: Check timestamp to see if data is super fresh
                 */
                return item
            }
        }
        Log.i(TAG, "Nothing found")
        return null
    }
}

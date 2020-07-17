package com.example.c19.model

import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.Locale.ROOT

/**
 *  CovidManager manages current data for
 *  states, countries, and global
 *
 *  @author Jeremy D. Jones
 *
 * TODO: Check timestamp to see if we need to attempt to refresh data
 *
 */
class CovidManager {
    private val TAG = "CovidManager"
    private val fileName = "favorites.dat"
    private val context = MyApp.applicationContext()
    private val file = File(context.filesDir, fileName)

    // keeping independent lists for states and countries.
    companion object {
        private var globalStats: GlobalCovid? = null
        private var states = mutableListOf<StateUsCovid>()
        private var countries = mutableListOf<CountryCovid>()
        private var favorites = mutableListOf<String>()
    }

    /**
     * runs when class is created.
     */
    init {
        if (file.exists()) {
            // read from file
            Log.i(TAG, "favorites.dat exists")
            favorites.clear()
            file.forEachLine { favorites.add(it) }
            Log.i(TAG, "read from file $favorites")
        } else {
            /*
            This line add's global if no data file exist
            and the favorites list is currently empty. That
            likely means this is the first run.
            */
            Log.i(TAG, "favorites.dat didn't exist, first run?")
            if (favorites.isEmpty()) addFavorite("global")
        }
    }

    /**
     * Retrieves either a state or a country
     *
     * @author Jeremy D. Jones
     * @param entityName
     * @return
     */
    fun getEntity(entityName: String): CovidEntity? {
        var entity: CovidEntity? = null
        // First try getting global stats
        if (entityName == "global") entity = getGlobal()
        // Second try fetching a state
        if (entity == null) entity = getState(entityName)
        // Last try fetching a country
        if (entity == null) entity = getCountry(entityName)
        return entity
    }

    private fun getGlobal(): GlobalCovid? {
        var global: GlobalCovid? = null
        if (globalStats != null) global = globalStats
        if (global == null) global = apiGlobalFetch()
        return global
    }

    /**
     * Retrieves a country
     *
     * @author Jeremy D. Jones
     * @param country
     * @return
     */
    private fun getCountry(country: String): CountryCovid? {
        // first search list for a country
        var countryCovid = searchCountryList(country)
        if (null != countryCovid) return countryCovid
        // if no match was found, fetch from the api
        countryCovid = apiCountryFetch(country)
        if (countryCovid != null) countries.add(countryCovid)
        // return null on failure to find a country
        return countryCovid
    }

    /**
     * Retrieves a state
     *
     * @author Jeremy D. Jones
     * @param state
     * @return returns null on failure, a StateUsCovid object on success
     */
    private fun getState(state: String): StateUsCovid? {
        // first search the list for a state
        var stateUsCovid = searchStateList(state)
        if (stateUsCovid != null) return stateUsCovid
        // if no match was found, fetch data from the api
        stateUsCovid = apiStateFetch(state)
        if (stateUsCovid != null) states.add(stateUsCovid)
        // return null on failure to find a state
        return stateUsCovid
    }

    /**
     * Returns global data from api fetch
     *
     * @return
     */
    private fun apiGlobalFetch(): GlobalCovid? {
        Log.i(TAG, "Fetching Global Data from API")
        var global: GlobalCovid? = null
        // Custom Gson Deserializer
        val customGson = GsonBuilder()
            .registerTypeAdapter(GlobalCovid::class.java, GlobalDeserializer())
            .create()
        // Retrofit 2 stuff
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.covid19api.com/")
            .addConverterFactory(GsonConverterFactory.create(customGson))
            .build()
        val service = retrofit.create(CovidApi::class.java)
        val call = service.getGlobal()
        val response = call.execute()
        if (response.code() == 200) global = response.body()
        return global
    }

    /**
     * apiStateFetch fetches data from the api
     *
     * @author Jeremy D. Jones
     * @param name
     */
    private fun apiStateFetch(name: String ): StateUsCovid? {
        var state: StateUsCovid? = null
        Log.i(TAG, "Making request to get $name")
        val retrofit = Retrofit.Builder()
            .baseUrl("https://corona.lmao.ninja/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(CovidApi::class.java)
        val call = service.getState(name)
        val response = call.execute()
        if (response.code() == 200) state = response.body()
        return state
    }

    /**
     * searches list for a given state
     *
     * @author Jeremy D. Jones
     * @param name
     * @return
     */
    private fun searchStateList(name: String ): StateUsCovid? {
        var state: StateUsCovid? = null
        Log.i(TAG, "Searching for $name")
        for (item in states) {
            if (item.name.toLowerCase(ROOT) == name.toLowerCase(ROOT)) {
                /**
                 * TODO: Check timestamp to see if data is super fresh
                 * I'll want to attempt to get fresh data, and if we can't get fresh data
                 * resort to using the old data.
                 */
                Log.i(TAG, "State found")
                state = item
            }
        }
    return state
    }

    /**
     * Fetches a country from API
     *
     * @author Jeremy D. Jones
     * @param name
     * @return
     */
    private fun apiCountryFetch(name: String): CountryCovid? {
        var country: CountryCovid? = null
        Log.i(TAG, "Making request to get $name")
        val retrofit = Retrofit.Builder()
            .baseUrl("https://corona.lmao.ninja/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(CovidApi::class.java)
        val call = service.getCountry(name)
        val response = call.execute()
        Log.i(TAG, "Response code: ${response.code()}")
        if (response.code() == 200) country = response.body()
        return country
    }

    /**
     * Searches memory for a country
     *
     * @author Jeremy D. Jones
     * @param country
     * @return
     */
    private fun searchCountryList(name: String): CountryCovid? {
        Log.i(TAG, "Searching for $name")
        var country: CountryCovid? = null
        for (item in countries) {
            if (item.name.toLowerCase(ROOT) == name.toLowerCase(ROOT)) {
                Log.i(TAG, "$country found")
                Log.i(TAG, item.toString())
                /**
                 * TODO: Check timestamp to see if data is super fresh
                 */
                Log.i(TAG,"Country found in list")
                country = item
                break
            }
        }
        return country
    }

    /**
     * Load the saved favorites
     *
     * @author Jeremy D. Jones
     * @return
     */
    fun getFavorites(): List<CovidEntity?> {
        return favorites.map { getEntity(it) }
    }

    /**
     * Add's a favorite to the favorite list
     *
     * @author Jeremy D. Jones
     * @param name
     */
    fun addFavorite(name: String) {
        Log.i(TAG, "Adding \"$name\" to favorites list")
        favorites.add(name)
        Log.i(TAG, "Saving updated favorite list to file")
        writeFavorite(favorites.last())
    }

    /**
     * Writes a favorite to disk
     *
     * @author Jeremy D. Jones
     * @param name
     */
    private fun writeFavorite(name: String) {
        file.appendText(name)
        file.appendText("\n")
    }

    /**
     * Deletes a favorite from favorite list
     *
     * @author Jeremy D. Jones
     * @param name
     */
    fun delFavorite(name: String) {
        Log.i(TAG, "Deleting \"$name\" from favorites list")
        favorites.remove(name)
        Log.i(TAG, "Saving updated favorite list to file")
        // Because this is simple, clearing the file first
        file.writeText("")
        // Now writing the new file to disk
        favorites.map { writeFavorite(it) }
    }
}

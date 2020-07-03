package com.example.c19.model

import android.util.Log
import java.util.Locale.ROOT

/**
 *  CovidManager, this is a tool that manages StateUsCovid data
 *  Likely a rename to StateCovidManager is in order, my plans have
 *  changed so that I"ll have a separate manager for each data type.
 *  Maybe a super manager called CovidManager to determine which manager to invoke.
 *
 *  @author Jeremy D. Jones
 *
 * TODO: Implement items on a function basis
 * TODO: Add disk load if list is empty
 * TODO: Check timestamp to see if we need to attempt to refresh data
 * TODO: Cleanup
 *
 *
 */
class CovidStateManager {
    private val TAG = "CovidStateManager"

    // This is the kotlin variation of a static member variable
    companion object {
        var states = mutableListOf<StateUsCovid>()
    }

    /**
     * This will be my main work horse and only public function.
     * Everything done in here right now, will be migrated out to
     * private functions and called from here.
     *
     * @param state
     * @return returns null on failure, a StateUsCovid object on success
     */
    fun getState(state: String): StateUsCovid? {
        /**
         * TODO: If list is empty load from disk
         */
        if (states.isEmpty()) {
            loadFromDisk()
        }
        // first search the list for a state
        // if a match is found, return it
        var stateUsCovid = searchList(state)
        if (stateUsCovid != null)
            return stateUsCovid
        // if no match was found, fetch data from the api
        stateUsCovid = apiStateFetch(state)
        if (stateUsCovid != null) {
            states.add(stateUsCovid)
            return stateUsCovid
        }
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
        val service = CovidStateApi.create()
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
     *
     * @param state
     * @return
     */
    private fun searchList(state: String ): StateUsCovid? {

        for (item in states) {
            Log.i(TAG, "Searching for $state")
            if (item.state.toLowerCase(ROOT) == state.toLowerCase(ROOT)) {
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
}

package com.example.c19

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
class CovidManager {
    private val TAG = "CovidManager"

/*
This is the kotlin variation of a static member variable
most things kotlin is more brief on, but something as simple as a static isn't
ha, that's irony
*/
    companion object {
        var states = mutableListOf<StateUsCovid>()
    }

    /**
     * TODO
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

        // first search the list for a state
        // if a match is found, return it
        for (item in states) {
            Log.i(TAG, "Searching for " + state)
            if (item.state.toLowerCase(ROOT) == state.toLowerCase(ROOT)) {
                Log.i(TAG, state + " found")
                Log.i(TAG, item.toString())
                /**
                 * TODO: Check timestamp to see if data is super fresh
                 * I'll want to attempt to get fresh data, and if we can't get fresh data
                 * resort to using the old data.
                 */
                return item
            }
        }
        /**
         * If we get here, no match was found, we need to request one from the API
         * I want to put this in it's own function, but had issues converting.
         * for now it remains here so we have something that works.
         * TODO: Implement API call in own private function
         **/
        Log.i(TAG, "Making request to get state California")
        val service = NovelCovidApi.create()
        val call = service.getState(state)
        Log.i(TAG, call.toString())
        val response = call.execute()
        if (response.code() == 200) {
            val stateUsCovid = response.body()
            if (stateUsCovid != null) {
                Log.i(TAG, stateUsCovid.toString())
                states.add(stateUsCovid)
            }
        }

        if (!states.isEmpty())
            return states.last()
        return null
    }

    /**
     * TODO Api call will be migrated to this function
     *
     * @param state
     */
    private fun apiStateFetch(state: String ) {
        /**
         * TODO: Implement API call here, getState() will get cleaned up to a series of functions
         */
    }
}
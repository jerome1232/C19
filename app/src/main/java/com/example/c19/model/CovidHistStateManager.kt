package com.example.c19.model

class CovidHistStateManager {
    private val TAG = "CovidHistStateManager"

    companion object {
        var state = mutableListOf<StateUsCovidHistorical>()
        var states = mutableListOf<MutableList<StateUsCovidHistorical>>()
    }

    fun getState(stateName: String): MutableList<StateUsCovidHistorical> {
        if (states.isEmpty()) {
                loadFromDisk()
            }
        
        state = searchList(stateName)
        if (state.isNotEmpty()) return state
        state = apiFetch(stateName)
        return mutableListOf<StateUsCovidHistorical>()
    }

    private fun searchList(stateName: String): MutableList<StateUsCovidHistorical> {
        TODO("Not yet implemented")
    }

    private fun loadFromDisk(): List<StateUsCovidHistorical> {
        TODO("Not yet implemented")
    }

    private fun apiFetch(stateName: String): MutableList<StateUsCovidHistorical> {
        TODO("Not yet implemented")
    }
}
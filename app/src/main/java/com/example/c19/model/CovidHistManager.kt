package com.example.c19.model

class CovidHistManager {
    private val TAG = "CovidHistManager"

    companion object {
        private var states = mutableListOf<List<CovidHistState>>()
        private var countries = mutableListOf<List<CovidHistCountry>>()
    }

    fun getHistEntity() : List<CovidHistEntity> {
        // stub
        return emptyList()
    }
}
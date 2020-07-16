package com.example.c19.presenter

/**
 * Mediates the interaction between the Historical Data fragment and the Historical data model
 * @author Rodrigo Iturralde
 */
interface HistoricalDataPresenter {
    /**
     * Retrieves historical data from model
     */
    fun getHistoricalData(entityName: String)
}
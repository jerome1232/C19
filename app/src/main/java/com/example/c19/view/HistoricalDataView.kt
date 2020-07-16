package com.example.c19.view

import com.example.c19.model.CovidHistEntity

/** Interface that the view shall implement
 * @author Rodrigo Iturralde
 */
interface HistoricalDataView {
    fun drawChart(covidEntities: List<CovidHistEntity>)
}